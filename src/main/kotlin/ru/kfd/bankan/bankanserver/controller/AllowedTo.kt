package ru.kfd.bankan.bankanserver.controller

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import ru.kfd.bankan.bankanserver.repository.*
import java.util.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

@Component
class AllowedTo(
    val authInfoRepository: AuthInfoRepository,
    val boardToAssignedUserMappingRepository: BoardToAssignedUserMappingRepository,
    val boardToListMappingRepository: BoardToListMappingRepository,
    val listToCardMappingRepository: ListToCardMappingRepository,
    val boardRepository: BoardRepository,
    val userToWorkspaceMappingRepository: UserToWorkspaceMappingRepository
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

    fun readByWorkspaceId(workspaceId: Int): Optional<Boolean> {
        val login = SecurityContextHolder.getContext().authentication.principal
        val creatorEntity = authInfoRepository.findByEmail(login.toString())
            ?: return Optional.empty()
        val userId = creatorEntity.userId
        val mapping = userToWorkspaceMappingRepository.findByWorkspaceId(workspaceId)
        if (mapping.isEmpty) return Optional.empty()
        return Optional.of(mapping.get().userId == userId)
    }

    fun readByBoardId(boardId: Int): Optional<Boolean> {
        val boardEntity = boardRepository.findById(boardId)
        if (boardEntity.isEmpty) return Optional.empty()
        if (boardEntity.get().isOpen) return Optional.of(true)
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

    // for users
    fun readUserInfo(): Boolean {
        return SecurityContextHolder.getContext().authentication.isAuthenticated
    }

    fun editUserInfo(userId: Int): Optional<Boolean> {
        val login = SecurityContextHolder.getContext().authentication.principal
        val creatorEntity = authInfoRepository.findByEmail(login.toString())
            ?: return Optional.empty()
        val currentUserId = creatorEntity.userId
        return Optional.of(currentUserId == userId)
    }
}

inline fun <reified Entity, Num> CrudRepository<Entity, Num>.safeFindById(
    id: Num,
    block: ((entity: Entity) -> Entity) = { it }
): Entity {
    val entity = findByIdOrNull(id) ?: throw IdNotFoundException("id = $id not found in ${Entity::class.simpleName}")
    return block(entity)
}

class IdNotFoundException(message: String) : RuntimeException(message)

inline infix fun <reified T : Any, reified R : Any> T.updateWithIfNotNull(other: R) {
    other::class.memberProperties.forEach { rightProp ->
        val leftProp = this::class.memberProperties.find { it.name == rightProp.name }!!
        if (leftProp is KMutableProperty<*>) {
            leftProp.setter.call(this, rightProp.getter.call(other))
        }
    }
}
