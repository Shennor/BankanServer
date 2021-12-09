package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.model.PasswordHash

interface PasswordRepository : CrudRepository<PasswordHash, Int>