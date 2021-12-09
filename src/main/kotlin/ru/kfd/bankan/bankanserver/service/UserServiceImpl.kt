package ru.kfd.bankan.bankanserver.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import ru.kfd.bankan.bankanserver.model.PasswordHash
import ru.kfd.bankan.bankanserver.model.UserInfo
import ru.kfd.bankan.bankanserver.repository.PasswordRepository
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository

class UserServiceImpl(
    val userRepo: UserInfoRepository,
    val passwordRepo: PasswordRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {

    override fun save(user: UserInfo, password: String) {
        userRepo.save(user)
        val hash = bCryptPasswordEncoder.encode(password)
        val hashMapObj = PasswordHash(null, user, hash)
        passwordRepo.save(hashMapObj)
    }

    override fun findByLogin(login: String): UserInfo {
        TODO("Not yet implemented")
    }

}