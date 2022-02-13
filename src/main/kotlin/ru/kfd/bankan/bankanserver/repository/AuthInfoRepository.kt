package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity

interface AuthInfoRepository : CrudRepository<AuthInfoEntity, Int> {
    fun findByEmail(email : String) : AuthInfoEntity?
    fun existsByEmail(email: String): Boolean
}

