package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.UserToWorkspaceMappingEntity

interface UserToWorkspaceMappingRepository : CrudRepository<UserToWorkspaceMappingEntity, Int> {
    fun findByWorkspaceId(workspaceId: Int): UserToWorkspaceMappingEntity?
    fun findUserToWorkspaceMappingEntitiesByWorkspaceId(workspaceId: Int): List<UserToWorkspaceMappingEntity>
}