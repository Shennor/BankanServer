package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.UserInfoEntity
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "card")
open class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "name", nullable = false, length = 20)
    open var name: String? = null

    @Column(name = "color", nullable = false)
    open var color: Int? = null

    @Column(name = "creation_data", nullable = false)
    open var creationData: LocalDate? = null

    @Column(name = "deadline")
    open var deadline: LocalDate? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    open var creator: UserInfoEntity? = null

    @Column(name = "card_content", nullable = false)
    open var cardContent: String? = null
}