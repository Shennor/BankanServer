package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.CardEntity

interface CardRepository : CrudRepository<CardEntity, Int>