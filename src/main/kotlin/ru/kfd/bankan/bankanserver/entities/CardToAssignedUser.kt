package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(
    name = "card_to_assigned_user", indexes = [
        Index(name = "user_id", columnList = "user_id"),
        Index(name = "card_id", columnList = "card_id")
    ]
)
@Entity
class CardToAssignedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserInfo? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    var card: Card? = null
}
