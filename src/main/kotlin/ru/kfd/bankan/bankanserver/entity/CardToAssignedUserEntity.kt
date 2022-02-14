package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.CardToAssignedUserEntity
import javax.persistence.*

@Entity
@Table(name = "card_to_assigned_user")
class CardToAssignedUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "user_id")
    var userId = 0

    @Basic
    @Column(name = "card_id")
    var cardId = 0
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CardToAssignedUserEntity
        if (id != that.id) return false
        if (userId != that.userId) return false
        return if (cardId != that.cardId) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + cardId
        return result
    }
}