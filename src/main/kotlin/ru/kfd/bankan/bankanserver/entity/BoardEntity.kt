package ru.kfd.bankan.bankanserver.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "board")
open class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "name", nullable = false, length = 20)
    open var name: String? = null

    @Lob
    @Column(name = "description", nullable = false)
    open var description: String? = null

    @Column(name = "creation_data", nullable = false)
    open var creationData: LocalDate? = null
}