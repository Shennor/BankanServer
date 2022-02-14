package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.UserToWorkspaceMappingEntity
import java.util.*

interface UserToWorkspaceMappingRepository : CrudRepository<UserToWorkspaceMappingEntity, Int>{
    fun findByWorkspaceId(workspaceId : Int) : Optional<UserToWorkspaceMappingEntity>
}