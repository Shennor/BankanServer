package ru.kfd.bankan.bankanserver.database.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "card")
open class CardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int = 0,

    @Column(name = "name", nullable = false, length = 4096)
    var name: String? = null,

    @Column(name = "color", nullable = false)
    var color: Int? = null,

    @Column(name = "creation_data", nullable = false)
    var creationData: LocalDate? = null,

    @Column(name = "deadline")
    var deadline: LocalDate? = null,

    @Column(name = "creator_id", nullable = false)
    var creatorId: Int? = null,

    @Column(name = "card_content", nullable = false, length = 4096)
    var cardContent: String? = null
)