package ru.kfd.bankan.bankanserver.service

import ru.kfd.bankan.bankanserver.entity.UserInfo

interface UserService {
    fun findByLogin(login: String): UserInfo
    fun save(user: UserInfo, password: String)
}