package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(name = "user_info")
@Entity
class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null
}
