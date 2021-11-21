package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(
    name = "user_settings", indexes = [
        Index(name = "user_id", columnList = "user_id", unique = true)
    ]
)
@Entity
class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserInfo? = null

    @Column(name = "settings", nullable = false)
    var settings: String? = null
}
