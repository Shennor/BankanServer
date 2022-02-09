package ru.kfd.bankan.bankanserver.repository

import org.springframework.data.repository.CrudRepository
import ru.kfd.bankan.bankanserver.entity.BoardEntity

interface BoardRepository : CrudRepository<BoardEntity, Int> {
    fun editName(id: Int, new_name: String): Unit {
        TODO()
    }
}