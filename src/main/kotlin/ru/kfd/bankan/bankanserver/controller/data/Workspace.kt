package ru.kfd.bankan.bankanserver.controller.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.payload.response.WorkspaceResponse
import ru.kfd.bankan.bankanserver.repository.BoardRepository
import ru.kfd.bankan.bankanserver.repository.WorkspaceRepository
import ru.kfd.bankan.bankanserver.repository.WorkspaceToBoardMappingRepository

// TODO
@RequestMapping("/api/workspace")
@RestController
class Workspace(
    val workspaceRepository: WorkspaceRepository,
    val workspaceToBoardMappingRepository: WorkspaceToBoardMappingRepository,
    val boardRepository: BoardRepository,
    val allowedTo: AllowedTo,
    val mapper: ObjectMapper
) {
    @GetMapping("/{workspaceId}")
    fun getWorkspace(@PathVariable workspaceId: Int): ResponseEntity<String> {
        // check if workspaceId exists
        val workspaceOptional = workspaceRepository.findById(workspaceId)
        if (workspaceOptional.isEmpty) return ResponseEntity(
            "Workspace with id $workspaceId does not exist",
            HttpStatus.NOT_FOUND
        )
        // check if user have permission to read workspace (it's their)
        val optional = allowedTo.readByWorkspaceId(workspaceId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get()) return ResponseEntity("You have no permission to read workspace", HttpStatus.FORBIDDEN)
        // read
        val workspaceEntity = workspaceOptional.get()
        val mappingList = workspaceToBoardMappingRepository.findAllByWorkspaceId(workspaceId)
        return ResponseEntity(
            mapper.writeValueAsString(
                WorkspaceResponse(
                    workspaceEntity.id,
                    workspaceEntity.name!!,
                    (mappingList.map { boardRepository.getById(it.boardId) }).toList()
                )
            ), HttpStatus.OK
        )
    }
}
