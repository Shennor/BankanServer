package ru.kfd.bankan.bankanserver.database.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.database.entity.UserToWorkspaceMappingEntity

interface UserToWorkspaceMappingRepository : CrudRepository<UserToWorkspaceMappingEntity, Int> {
    fun findByWorkspaceId(workspaceId: Int): UserToWorkspaceMappingEntity?
    fun findUserToWorkspaceMappingEntitiesByWorkspaceId(workspaceId: Int): List<UserToWorkspaceMappingEntity>
}