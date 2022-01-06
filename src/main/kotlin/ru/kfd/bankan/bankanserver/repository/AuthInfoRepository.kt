package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity
import ru.kfd.bankan.bankanserver.entity.UserInfoEntity

interface AuthInfoRepository : CrudRepository<AuthInfoEntity, Int> {
    fun findByEmail(login: String): AuthInfoEntity
    fun existsByEmail(login: String): Boolean
}

