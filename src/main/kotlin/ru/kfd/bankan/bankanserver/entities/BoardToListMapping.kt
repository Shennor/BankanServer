package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(
    name = "board_to_list_mapping", indexes = [
        Index(name = "list_id", columnList = "list_id"),
        Index(name = "board_id", columnList = "board_id")
    ]
)
@Entity
class BoardToListMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Board? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    var list: List? = null

    @Column(name = "index_of_list_in_board", nullable = false)
    var indexOfListInBoard: Int? = null
}
