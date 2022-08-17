package ru.kfd.bankan.bankanserver.database.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.database.entity.WorkspaceToBoardMappingEntity

interface WorkspaceToBoardMappingRepository : CrudRepository<WorkspaceToBoardMappingEntity, Int> {
    fun findAllByWorkspaceId(workspaceId: Int): MutableList<WorkspaceToBoardMappingEntity>
    fun countWorkspaceToBoardMappingEntitiesByWorkspaceId(workspaceId: Int): Int
    fun deleteWorkspaceToBoardMappingEntityByBoardId(boardId: Int)
}