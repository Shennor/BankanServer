package ru.kfd.bankan.bankanserver.controller.data

import org.hibernate.annotations.Parameter
import org.springframework.data.jpa.repository.Query
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.controller.safeFindById
import ru.kfd.bankan.bankanserver.payload.response.WorkspaceResponse
import ru.kfd.bankan.bankanserver.repository.BoardRepository
import ru.kfd.bankan.bankanserver.repository.UserToWorkspaceMappingRepository
import ru.kfd.bankan.bankanserver.repository.WorkspaceRepository
import ru.kfd.bankan.bankanserver.repository.WorkspaceToBoardMappingRepository

// TODO
@CrossOrigin(origins = ["http://localhost:8080"])
@RequestMapping("/api/workspace")
@RestController
class Workspace(
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceToBoardMappingRepository: WorkspaceToBoardMappingRepository,
    private val boardRepository: BoardRepository,
    private val userToWorkspaceMappingRepository: UserToWorkspaceMappingRepository,
    private val allowedTo: AllowedTo,
) {
    @GetMapping("/{workspaceId}")
    fun getWorkspace(@PathVariable workspaceId: Int): WorkspaceResponse {
        // check if workspaceId exists
        val workspaceEntity = workspaceRepository.safeFindById(workspaceId)
        // check if user have permission to read workspace (it's their)
        allowedTo.readByWorkspaceId(workspaceId)
        // read
        val mappingList = workspaceToBoardMappingRepository.findAllByWorkspaceId(workspaceId)
        return WorkspaceResponse(
            workspaceEntity.id,
            workspaceEntity.name!!,
            (mappingList.map { boardRepository.safeFindById(it.boardId) }).toList()
        )
    }

    @GetMapping("/user/{userId}")
    fun getWorkspaceByUserId(@PathVariable userId: Int): WorkspaceResponse {
        // check if person exists
        val mappingEntity = userToWorkspaceMappingRepository.safeFindById(userId);
        return getWorkspace(mappingEntity.workspaceId!!)
    }
}
