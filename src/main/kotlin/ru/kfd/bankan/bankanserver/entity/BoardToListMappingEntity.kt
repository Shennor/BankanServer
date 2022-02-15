package ru.kfd.bankan.bankanserver.entity

import javax.persistence.*

@Entity
@Table(name = "board_to_list_mapping")
open class BoardToListMappingEntity (
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id: Int = 0,

    @Basic
    @Column(name = "board_id", nullable = false)
    var boardId: Int = 0,

    @Basic
    @Column(name = "list_id", nullable = false)
    var listId: Int = 0,

    @Basic
    @Column(name = "index_of_list_in_board", nullable = false)
    var indexOfListInBoard: Int = 0,
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BoardToListMappingEntity
        if (id != that.id) return false
        if (boardId != that.boardId) return false
        if (listId != that.listId) return false
        return indexOfListInBoard == that.indexOfListInBoard
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + boardId
        result = 31 * result + listId
        result = 31 * result + indexOfListInBoard
        return result
    }
}