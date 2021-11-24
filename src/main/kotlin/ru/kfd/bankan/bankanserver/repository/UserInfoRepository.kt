package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.model.UserInfo

interface UserInfoRepository : CrudRepository<UserInfo, Int>
