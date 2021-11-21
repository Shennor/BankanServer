package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(name = "list")
@Entity
class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null
}
