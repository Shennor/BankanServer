package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.UserInfo

interface AuthInfoRepository : CrudRepository<AuthInfo, Int>{
    fun findByLogin(login : String) : UserInfo;
//    fun findByAccessToken(accessToken: String): UserInfo?
//    fun findByUsername(username: String): UserInfo?
//    fun existsByUsername(username: String): Boolean
}
