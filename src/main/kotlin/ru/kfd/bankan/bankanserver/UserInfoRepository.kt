package ru.kfd.bankan.bankanserver

import org.springframework.data.repository.CrudRepository

interface UserInfoRepository :
    CrudRepository<UserInfo, Int>
