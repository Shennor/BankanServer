package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.WorkspaceEntity
import ru.kfd.bankan.bankanserver.entity.WorkspaceToBoardMappingEntity
import javax.persistence.*

@Entity
@Table(name = "workspace_to_board_mapping")
class WorkspaceToBoardMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "workspace_id")
    var workspaceId = 0

    @Basic
    @Column(name = "board_id")
    var boardId = 0

    @Basic
    @Column(name = "index_of_board_in_workspace")
    var indexOfBoardInWorkspace = 0

    @ManyToOne
    @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable = false)
    var workspaceByWorkspaceId: WorkspaceEntity? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as WorkspaceToBoardMappingEntity
        if (id != that.id) return false
        if (workspaceId != that.workspaceId) return false
        if (boardId != that.boardId) return false
        return if (indexOfBoardInWorkspace != that.indexOfBoardInWorkspace) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + workspaceId
        result = 31 * result + boardId
        result = 31 * result + indexOfBoardInWorkspace
        return result
    }
}