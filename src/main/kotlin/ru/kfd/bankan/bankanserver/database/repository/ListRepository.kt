package ru.kfd.bankan.bankanserver.database.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.database.entity.ListEntity

interface ListRepository : CrudRepository<ListEntity, Int>