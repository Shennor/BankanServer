package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.BoardToListMappingEntity
import javax.persistence.*

@Entity
@Table(name = "board_to_list_mapping")
class BoardToListMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "board_id")
    var boardId = 0

    @Basic
    @Column(name = "list_id")
    var listId = 0

    @Basic
    @Column(name = "index_of_list_in_board")
    var indexOfListInBoard = 0
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BoardToListMappingEntity
        if (id != that.id) return false
        if (boardId != that.boardId) return false
        if (listId != that.listId) return false
        return if (indexOfListInBoard != that.indexOfListInBoard) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + boardId
        result = 31 * result + listId
        result = 31 * result + indexOfListInBoard
        return result
    }
}