package ru.kfd.bankan.bankanserver.rest.controller

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import ru.kfd.bankan.bankanserver.database.repository.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties


@ResponseStatus(HttpStatus.NOT_FOUND)
class IdNotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.FORBIDDEN)
class ResourceNotAllowedToUser(message: String) : RuntimeException(message)

@Component
class AllowedTo(
    private val authInfoRepository: AuthInfoRepository,
    private val boardToAssignedUserMappingRepository: BoardToAssignedUserMappingRepository,
    private val boardToListMappingRepository: BoardToListMappingRepository,
    private val listToCardMappingRepository: ListToCardMappingRepository,
    private val boardRepository: BoardRepository,
    private val userToWorkspaceMappingRepository: UserToWorkspaceMappingRepository
) {
    fun safeCurrentUser(): ru.kfd.bankan.bankanserver.database.entity.AuthInfoEntity {
        val principal = SecurityContextHolder.getContext().authentication.principal

        val username: String = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }
        return authInfoRepository.findByEmail(username)
            ?: throw UserNotFoundException("User with email: $username not found")
    }

    fun writeByWorkspaceId(workspaceId: Int) {
        val creator = safeCurrentUser()
        if (!userToWorkspaceMappingRepository.findUserToWorkspaceMappingEntitiesByWorkspaceId(workspaceId)
                .any { it.userId == creator.userId }
        )
            throw ResourceNotAllowedToUser("Workspace not allowed to user with email ${creator.email}")
    }

    fun writeByBoardId(boardId: Int) {
        val creator = safeCurrentUser()
        if (!boardToAssignedUserMappingRepository.findBoardToAssignedUserEntitiesByBoardId(boardId)
                .any { it.userId == creator.userId }
        )
            throw ResourceNotAllowedToUser("Board not allowed to user with email ${creator.email}")
    }

    fun writeByListId(listId: Int) {
        val boardToListMappingEntity = boardToListMappingRepository.findByListId(listId)
            ?: throw IdNotFoundException("List with $listId not owned by any board")
        val boardId = boardToListMappingEntity.boardId
        writeByBoardId(boardId)
    }

    // allowed to change this card in all lists
    fun writeByCardId(cardId: Int) {
        val listOfListToCardMappingEntities = listToCardMappingRepository.findAllByCardId(cardId)
        if (listOfListToCardMappingEntities.size == 0)
            throw IdNotFoundException("Card with $cardId not owned by any list")

        for (i in listOfListToCardMappingEntities) {
            val listId = i.listId
            writeByListId(listId)
        }
    }

    fun readByWorkspaceId(workspaceId: Int) {
        val user = safeCurrentUser()
        val mapping = userToWorkspaceMappingRepository.findByWorkspaceId(workspaceId)
            ?: throw IdNotFoundException("Workspace with id $workspaceId not owned by any User")
        if (mapping.userId != user.userId)
            throw ResourceNotAllowedToUser("Workspace with id $workspaceId not allowed to user with email ${user.email}")
    }

    fun readByBoardId(boardId: Int) {
        val boardEntity = boardRepository.findById(boardId)
        if (boardEntity.isEmpty)
            throw IdNotFoundException("Board with id $boardId not found")
        //writeByBoardId(boardId)
    }

    fun readByListId(listId: Int) {
        val mappingEntity = boardToListMappingRepository.findByListId(listId)
            ?: throw IdNotFoundException("List with id $listId not owned by any board")
        readByBoardId(mappingEntity.boardId)
    }

    // allowed if user have permission at least to one board on which the card is
    fun readByCardId(cardId: Int) {
        val listOfMappingEntities = listToCardMappingRepository.findAllByCardId(cardId)
        if (listOfMappingEntities.size == 0) throw IdNotFoundException("Card with id $cardId is not owned by any list")
        for (i in listOfMappingEntities) {
            readByListId(i.listId)
        }
    }

    // for users
    fun readUserInfo(): Boolean {
        return SecurityContextHolder.getContext().authentication.isAuthenticated
    }

    fun editUserInfo(userId: Int) {
        val creatorEntity = safeCurrentUser()
        val currentUserId = creatorEntity.userId
        if (currentUserId != userId)
            throw ResourceNotAllowedToUser("User is not owner")
    }
}

inline fun <reified Entity, Num> CrudRepository<Entity, Num>.safeFindById(
    id: Num,
    block: ((entity: Entity) -> Entity) = { it }
): Entity {
    val entity = findByIdOrNull(id) ?: throw IdNotFoundException("id = $id not found as ${Entity::class.simpleName}")
    return block(entity)
}

inline infix fun <reified T : Any, reified R : Any> T.updateWithIfNotNull(other: R) {
    other::class.memberProperties.forEach { rightProp ->
        val leftProp = this::class.memberProperties.find { it.name == rightProp.name }!!
        if (leftProp is KMutableProperty<*>) {
            leftProp.setter.call(this, rightProp.getter.call(other))
        }
    }
}
