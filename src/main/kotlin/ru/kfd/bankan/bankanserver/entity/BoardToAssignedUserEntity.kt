package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.BoardToAssignedUserEntity
import javax.persistence.*

@Entity
@Table(name = "board_to_assigned_user", schema = "bankan", catalog = "")
class BoardToAssignedUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "user_id")
    var userId = 0

    @Basic
    @Column(name = "board_id")
    var boardId = 0
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BoardToAssignedUserEntity
        if (id != that.id) return false
        if (userId != that.userId) return false
        return if (boardId != that.boardId) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + boardId
        return result
    }
}