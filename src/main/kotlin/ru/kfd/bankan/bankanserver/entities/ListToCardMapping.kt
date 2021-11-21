package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(
    name = "list_to_card_mapping", indexes = [
        Index(name = "list_id", columnList = "list_id"),
        Index(name = "card_id", columnList = "card_id")
    ]
)
@Entity
class ListToCardMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    var list: List? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    var card: Card? = null

    @Column(name = "index_of_card_in_list", nullable = false)
    var indexOfCardInList: Int? = null
}
