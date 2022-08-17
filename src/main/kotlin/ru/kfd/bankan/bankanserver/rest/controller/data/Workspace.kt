package ru.kfd.bankan.bankanserver.rest.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.database.repository.BoardRepository
import ru.kfd.bankan.bankanserver.database.repository.UserToWorkspaceMappingRepository
import ru.kfd.bankan.bankanserver.database.repository.WorkspaceRepository
import ru.kfd.bankan.bankanserver.database.repository.WorkspaceToBoardMappingRepository
import ru.kfd.bankan.bankanserver.rest.controller.AllowedTo
import ru.kfd.bankan.bankanserver.rest.controller.safeFindById
import ru.kfd.bankan.bankanserver.rest.payload.response.WorkspaceResponse

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
        val mappingEntity = userToWorkspaceMappingRepository.safeFindById(userId)
        return getWorkspace(mappingEntity.workspaceId!!)
    }
}
