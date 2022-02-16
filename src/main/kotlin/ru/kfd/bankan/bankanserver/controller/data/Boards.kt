package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.controller.safeFindById
import ru.kfd.bankan.bankanserver.controller.updateWithIfNotNull
import ru.kfd.bankan.bankanserver.entity.WorkspaceToBoardMappingEntity
import ru.kfd.bankan.bankanserver.payload.request.BoardCreateRequest
import ru.kfd.bankan.bankanserver.payload.request.BoardUpdateRequest
import ru.kfd.bankan.bankanserver.payload.request.asEntity
import ru.kfd.bankan.bankanserver.payload.response.BoardInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.ListInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.asListInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.asResponse
import ru.kfd.bankan.bankanserver.repository.BoardRepository
import ru.kfd.bankan.bankanserver.repository.BoardToListMappingRepository
import ru.kfd.bankan.bankanserver.repository.ListRepository
import ru.kfd.bankan.bankanserver.repository.WorkspaceToBoardMappingRepository


// TODO
@RequestMapping("/api/board/")
@RestController
class Boards(
    private val workspaceToBoardMappingRepository: WorkspaceToBoardMappingRepository,
    private val boardRepository: BoardRepository,
    private val boardToListMappingRepository: BoardToListMappingRepository,
    private val listRepository: ListRepository,
    private val allowedTo: AllowedTo
) {
    @PostMapping("/{workspaceId}")
    fun create(@PathVariable workspaceId: Int, @RequestBody body: BoardCreateRequest) {
        allowedTo.writeByWorkspaceId(workspaceId)
        val boardEntity = boardRepository.save(body.asEntity)
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
        boardRepository
        return boardRepository.safeFindById(boardId).asResponse
    }

    @GetMapping("/{boardId}")
    fun read(@PathVariable boardId: Int): List<Pair<Int, ListInfoResponse>> {
        allowedTo.readByBoardId(boardId)
        return boardToListMappingRepository.findBoardToListMappingEntitiesByBoardId(boardId).map {
            it.listId to listRepository.safeFindById(it.listId).asListInfoResponse
        }
    }

    @GetMapping("/edit/{boardId}")
    fun update(@PathVariable boardId: Int, @RequestBody body: BoardUpdateRequest) {
        allowedTo.writeByBoardId(boardId)
        val entity = boardRepository.safeFindById(boardId)

        entity updateWithIfNotNull body
////        entity.name = body.name ?: entity.name
//        if (body.name != null) entity.name = body.name
//        if (body.description != null) entity.description = body.description
        boardRepository.save(entity)
    }

    @GetMapping("/delete/{boardId}")
    fun delete(@PathVariable boardId: Int) {
        allowedTo.writeByBoardId(boardId)
        boardRepository.deleteById(boardId)
        workspaceToBoardMappingRepository.deleteWorkspaceToBoardMappingEntityByBoardId(boardId)
        // TODO("delete nested structures")
    }
}
