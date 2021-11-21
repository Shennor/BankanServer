package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(
    name = "board_to_assigned_user", indexes = [
        Index(name = "user_id", columnList = "user_id"),
        Index(name = "board_id", columnList = "board_id")
    ]
)
@Entity
class BoardToAssignedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserInfo? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Board? = null
}
