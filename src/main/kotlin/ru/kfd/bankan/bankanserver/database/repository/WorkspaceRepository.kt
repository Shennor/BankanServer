package ru.kfd.bankan.bankanserver.database.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.database.entity.WorkspaceEntity

interface WorkspaceRepository : CrudRepository<WorkspaceEntity, Int>