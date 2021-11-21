package ru.kfd.bankan.bankanserver.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.Repository
import ru.kfd.bankan.bankanserver.entities.Board
import ru.kfd.bankan.bankanserver.entities.BoardToListMapping

interface BoardToListsMappingRepository :
    Repository<BoardToListMapping, Int> {
    fun findAllByBoard_Id(board_id: Int)
}

interface BoardsRepository : CrudRepository<Board, Int> {
    fun findByName(name: String): Board
}
