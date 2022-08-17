package ru.kfd.bankan.bankanserver.database.entity

import javax.persistence.*

@Entity
@Table(name = "workspace_to_board_mapping")
class WorkspaceToBoardMappingEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    var id: Int = 0,

    @Basic
    @Column(name = "workspace_id", nullable = false)
    var workspaceId: Int,

    @Basic
    @Column(name = "board_id", nullable = false)
    var boardId: Int,

    @Basic
    @Column(name = "index_of_board_in_workspace", nullable = false)
    var indexOfBoardInWorkspace: Int
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as WorkspaceToBoardMappingEntity
        if (id != that.id) return false
        if (workspaceId != that.workspaceId) return false
        if (boardId != that.boardId) return false
        return indexOfBoardInWorkspace == that.indexOfBoardInWorkspace
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + workspaceId
        result = 31 * result + boardId
        result = 31 * result + indexOfBoardInWorkspace
        return result
    }
}