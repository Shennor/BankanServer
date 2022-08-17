package ru.kfd.bankan.bankanserver.database.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.database.entity.CardEntity

interface CardRepository : CrudRepository<CardEntity, Int>