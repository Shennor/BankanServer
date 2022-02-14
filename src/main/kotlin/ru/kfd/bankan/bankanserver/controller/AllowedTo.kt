package ru.kfd.bankan.bankanserver.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import ru.kfd.bankan.bankanserver.repository.*
import java.util.*

@Component
class AllowedTo(
    val authInfoRepository: AuthInfoRepository,
    val boardToAssignedUserMappingRepository: BoardToAssignedUserMappingRepository,
    val boardToListMappingRepository: BoardToListMappingRepository,
    val listToCardMappingRepository: ListToCardMappingRepository,
    val boardRepository: BoardRepository
) {

    fun writeByBoardId(boardId: Int): Optional<Boolean> {
        val login = SecurityContextHolder.getContext().authentication.principal
        val creatorEntity = authInfoRepository.findByEmail(login.toString())
            ?: return Optional.empty()
        val creatorId = creatorEntity.userId
        val users = boardToAssignedUserMappingRepository.getAllByBoardId(boardId)
        if (users.find { it.userId == creatorId } == null) return Optional.of(false)
        return Optional.of(true)
    }

    fun writeByListId(listId: Int): Optional<Boolean> {
        val boardToListMappingEntity = boardToListMappingRepository.findByListId(listId)
            ?: return Optional.empty()
        val boardId = boardToListMappingEntity.boardId
        return writeByBoardId(boardId)
    }

    // allowed to change this card in all lists
    fun writeByCardId(cardId: Int): Optional<Boolean> {
        val listOfListToCardMappingEntities = listToCardMappingRepository.findAllByCardId(cardId)
        if (listOfListToCardMappingEntities.size == 0) return Optional.empty()
        for (i in listOfListToCardMappingEntities) {
            val listId = i.listId
            val optional = writeByListId(listId)
            if (optional.isEmpty) return Optional.empty()
            if (!optional.get()) Optional.of(false)
        }
        return Optional.of(true)
    }

    fun readByBoardId(boardId: Int): Optional<Boolean> {
        val boardEntity = boardRepository.findById(boardId)
        if (boardEntity.isEmpty) return Optional.empty()
        if (boardEntity.get().isOpen != 0.toByte()) return Optional.of(true)
        return writeByBoardId(boardId)
    }

    fun readByListId(listId: Int): Optional<Boolean> {
        val mappingEntity = boardToListMappingRepository.findByListId(listId)
            ?: return Optional.empty()
        return readByBoardId(mappingEntity.boardId)
    }

    // allowed if user have permission at least to one board on which the card is
    fun readByCardId(cardId: Int): Optional<Boolean> {
        val listOfMappingEntities = listToCardMappingRepository.findAllByCardId(cardId)
        if (listOfMappingEntities.size == 0) return Optional.empty()
        var allowed = false
        var exist = false
        for (i in listOfMappingEntities) {
            val optional = readByListId(i.listId)
            if (optional.isPresent) {
                exist = true
                if (optional.get()) {
                    allowed = true
                    break
                }
            }
        }
        if (!exist) return Optional.empty()
        return Optional.of(allowed)
    }
}