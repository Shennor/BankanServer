package ru.kfd.bankan.bankanserver.database.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.database.entity.BoardToAssignedUserEntity

interface BoardToAssignedUserMappingRepository : CrudRepository<BoardToAssignedUserEntity, Int> {
    fun findBoardToAssignedUserEntitiesByBoardId(boardId: Int): List<BoardToAssignedUserEntity>
    fun deleteAllByBoardIdAndUserId(boardId: Int, userId: Int)
    fun existsByBoardId(boardId: Int): Boolean
}