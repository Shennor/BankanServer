package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(
    name = "password_hash", indexes = [
        Index(name = "user_id", columnList = "user_id")
    ]
)
@Entity
class PasswordHash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserInfo? = null

    @Column(name = "hash", nullable = false, length = 16)
    var hash: String? = null
}
