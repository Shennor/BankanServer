package ru.kfd.bankan.bankanserver.entities

import java.time.LocalDate
import javax.persistence.*

@Table(name = "board")
@Entity
class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @Column(name = "name", nullable = false, length = 20)
    var name: String? = null

    @Lob
    @Column(name = "description", nullable = false)
    var description: String? = null

    @Column(name = "creation_data", nullable = false)
    var creationData: LocalDate? = null
}
