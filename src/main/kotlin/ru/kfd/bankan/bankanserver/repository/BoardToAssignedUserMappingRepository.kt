package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.BoardToAssignedUserEntity

interface BoardToAssignedUserMappingRepository : CrudRepository<BoardToAssignedUserEntity, Int> {
    fun findBoardToAssignedUserEntitiesByBoardId(boardId: Int): List<BoardToAssignedUserEntity>
}