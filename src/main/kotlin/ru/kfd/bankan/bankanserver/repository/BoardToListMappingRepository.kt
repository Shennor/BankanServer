package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.BoardToListMappingEntity

interface BoardToListMappingRepository : CrudRepository<BoardToListMappingEntity, Int> {
    fun findByListId(listId: Int): BoardToListMappingEntity?
    fun findBoardToListMappingEntitiesByBoardId(boardId: Int): List<BoardToListMappingEntity>
    fun countBoardToListMappingEntitiesByBoardId(boardId: Int): Int
}