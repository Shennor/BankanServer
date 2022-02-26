package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.controller.safeFindById
import ru.kfd.bankan.bankanserver.controller.updateWithIfNotNull
import ru.kfd.bankan.bankanserver.entity.BoardEntity
import ru.kfd.bankan.bankanserver.entity.BoardToAssignedUserEntity
import ru.kfd.bankan.bankanserver.entity.WorkspaceToBoardMappingEntity
import ru.kfd.bankan.bankanserver.payload.request.BoardCreateRequest
import ru.kfd.bankan.bankanserver.payload.request.BoardUpdateRequest
import ru.kfd.bankan.bankanserver.payload.request.asEntity
import ru.kfd.bankan.bankanserver.payload.response.BoardInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.ListInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.asListInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.asResponse
import ru.kfd.bankan.bankanserver.repository.*
import java.sql.Date
import java.sql.Time
import java.time.Instant.now
import java.time.LocalDate
import java.util.*


// TODO
@RequestMapping("/api/board/")
@RestController
class Boards(
    private val workspaceToBoardMappingRepository: WorkspaceToBoardMappingRepository,
    private val boardRepository: BoardRepository,
    private val boardToListMappingRepository: BoardToListMappingRepository,
    private val boardToAssignedUserMappingRepository: BoardToAssignedUserMappingRepository,
    private val listRepository: ListRepository,
    private val allowedTo: AllowedTo
) {
    @PostMapping("/{workspaceId}")
    fun create(@PathVariable workspaceId: Int, @RequestBody body: BoardCreateRequest) {
        allowedTo.writeByWorkspaceId(workspaceId)
        val boardEntity = boardRepository.save(body.asEntity)
        boardToAssignedUserMappingRepository.save(
            BoardToAssignedUserEntity(
                boardId = boardEntity.id,
                userId = allowedTo.safeCurrentUser().userId
            )
        )
        workspaceToBoardMappingRepository.save(
            WorkspaceToBoardMappingEntity(
                workspaceId = workspaceId,
                boardId = boardEntity.id,
                indexOfBoardInWorkspace = workspaceToBoardMappingRepository.countWorkspaceToBoardMappingEntitiesByWorkspaceId(
                    workspaceId
                )
            )
        )
    }

    @GetMapping("/info/{boardId}")
    fun readInfo(@PathVariable boardId: Int): BoardInfoResponse {
        allowedTo.readByBoardId(boardId)
        return boardRepository.safeFindById(boardId).asResponse
    }

    @GetMapping("/{boardId}")
    fun read(@PathVariable boardId: Int): List<Pair<Int, ListInfoResponse>> {
        allowedTo.readByBoardId(boardId)
        return boardToListMappingRepository.findBoardToListMappingEntitiesByBoardId(boardId).map {
            it.listId to listRepository.safeFindById(it.listId).asListInfoResponse
        }
    }

    // TODO: Test this
    @PatchMapping("/edit/{boardId}")
    fun update(@PathVariable boardId: Int, @RequestBody body: BoardUpdateRequest) {
        allowedTo.writeByBoardId(boardId)
        val entity = boardRepository.safeFindById(boardId)
        entity updateWithIfNotNull body
//        entity.name = body.name ?: entity.name
//        if (body.name != null) entity.name = body.name
//        if (body.description != null) entity.description = body.description
        boardRepository.save(entity)
    }

    // TODO: Test this
    @DeleteMapping("/delete/{boardId}")
    fun delete(@PathVariable boardId: Int) {
        allowedTo.writeByBoardId(boardId)
        workspaceToBoardMappingRepository.deleteWorkspaceToBoardMappingEntityByBoardId(boardId)
        boardToAssignedUserMappingRepository.deleteAllByBoardIdAndUserId(boardId, allowedTo.safeCurrentUser().userId)
        if(!boardToAssignedUserMappingRepository.existsByBoardId(boardId))
            boardRepository.deleteById(boardId)
        // TODO("delete nested structures")
    }
}
