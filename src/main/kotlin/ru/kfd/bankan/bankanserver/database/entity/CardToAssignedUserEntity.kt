package ru.kfd.bankan.bankanserver.database.entity

import javax.persistence.*

@Entity
@Table(name = "card_to_assigned_user")
class CardToAssignedUserEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id: Int = 0,

    @Basic
    @Column(name = "user_id", nullable = false)
    var userId: Int = 0,

    @Basic
    @Column(name = "card_id", nullable = false)
    var cardId: Int = 0
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CardToAssignedUserEntity
        if (id != that.id) return false
        if (userId != that.userId) return false
        return cardId == that.cardId
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + cardId
        return result
    }
}