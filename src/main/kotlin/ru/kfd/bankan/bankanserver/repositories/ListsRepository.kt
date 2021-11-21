package ru.kfd.bankan.bankanserver.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.Repository
import ru.kfd.bankan.bankanserver.entities.List
import ru.kfd.bankan.bankanserver.entities.ListToCardMapping

interface ListToCardsMappingRepository :
    Repository<ListToCardMapping, Int> {
    fun findAllByList(list: List)
}

interface ListsRepository : CrudRepository<List, Int> {

}
