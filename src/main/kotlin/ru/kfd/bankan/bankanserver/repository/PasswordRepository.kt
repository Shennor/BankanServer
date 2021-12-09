package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.PasswordHash

interface PasswordRepository : CrudRepository<PasswordHash, Int>{
    fun findByUserId(userId : Int) : PasswordHash
}