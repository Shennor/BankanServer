package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity

interface AuthInfoRepository : CrudRepository<AuthInfoEntity, Int> {
    fun findByLogin(login: String): AuthInfoEntity
    fun existsByLogin(login: String): Boolean
//    fun findByAccessToken(accessToken: String): UserInfo?
//    fun findByUsername(username: String): UserInfo?
//    fun existsByUsername(username: String): Boolean
}
