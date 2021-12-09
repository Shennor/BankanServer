package ru.kfd.bankan.bankanserver.service

import ru.kfd.bankan.bankanserver.model.UserInfo

interface UserService{
    fun save(user : UserInfo) : Unit
    fun findByLogin(login : String) : UserInfo
}