package ru.kfd.bankan.bankanserver.entity

import javax.persistence.*

@Entity
@Table(name = "board_to_assigned_user")
class BoardToAssignedUserEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id: Int = 0,

    @Basic
    @Column(name = "user_id", nullable = false)
    var userId: Int = 0,

    @Basic
    @Column(name = "board_id", nullable = false)
    var boardId: Int = 0
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BoardToAssignedUserEntity
        if (id != that.id) return false
        if (userId != that.userId) return false
        return boardId == that.boardId
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + boardId
        return result
    }
}