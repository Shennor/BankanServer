package ru.kfd.bankan.bankanserver.repository;

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.ListEntity

interface ListEntityRepository : CrudRepository<ListEntity, Int>