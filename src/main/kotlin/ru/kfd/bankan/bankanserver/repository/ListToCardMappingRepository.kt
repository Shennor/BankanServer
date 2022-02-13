package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.ListToCardMappingEntity

interface ListToCardMappingRepository : CrudRepository<ListToCardMappingEntity, Int>{
    fun findByCardId(cardId: Int): ListToCardMappingEntity
    fun getAllByListId(listId : Int): MutableList<ListToCardMappingEntity>
    fun getAllByListIdEqualsAndAndIndexOfCardInListGreaterThanEqual(listId: Int, index : Int) : MutableList<ListToCardMappingEntity>
}