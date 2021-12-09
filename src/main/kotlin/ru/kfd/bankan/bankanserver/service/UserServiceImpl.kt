package ru.kfd.bankan.bankanserver.service

import ru.kfd.bankan.bankanserver.model.UserInfo
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository

class UserServiceImpl (
    val userRepo : UserInfoRepository
        ): UserService
{
    override fun save(user: UserInfo) {
        TODO("Not yet implemented")
    }

    override fun findByLogin(login: String) : UserInfo {
        TODO("Not yet implemented")
    }

}