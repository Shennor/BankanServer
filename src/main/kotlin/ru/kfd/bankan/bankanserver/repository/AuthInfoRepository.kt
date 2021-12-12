package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity

interface AuthInfoRepository : CrudRepository<AuthInfoEntity, Int> {
    fun findByLogin(login: String): AuthInfoEntity
    fun existsByLogin(login: String): Boolean
}
