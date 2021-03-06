package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.UserSettingsEntity

interface UserSettingsRepository : CrudRepository<UserSettingsEntity, Int> {
    fun findByUserId(userId: Int) : UserSettingsEntity?
}