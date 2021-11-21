package ru.kfd.bankan.bankanserver.entities

import javax.persistence.*

@Table(
    name = "user_to_table_mapping", indexes = [
        Index(name = "user_id", columnList = "user_id"),
        Index(name = "board_id", columnList = "board_id")
    ]
)
@Entity
class UserToTableMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserInfo? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Card? = null

    @Column(name = "index_of_board_in_workspace", nullable = false)
    var indexOfBoardInWorkspace: Int? = null
}
