package ru.kfd.bankan.bankanserver.database.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.database.entity.ListToCardMappingEntity

interface ListToCardMappingRepository : CrudRepository<ListToCardMappingEntity, Int> {
    fun findAllByCardId(cardId: Int): MutableList<ListToCardMappingEntity>
    fun getAllByListId(listId: Int): MutableList<ListToCardMappingEntity>
    fun existsByListIdAndCardId(listId: Int, cardId: Int): Boolean
    fun deleteByListIdAndCardId(listId: Int, cardId: Int)
    fun deleteAllByCardId(cardId: Int)
    fun countListToCardMappingEntitiesByListId(listId: Int): Int
}