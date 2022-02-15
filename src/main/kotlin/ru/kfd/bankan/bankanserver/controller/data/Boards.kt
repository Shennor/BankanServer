package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
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
    val workspaceToBoardMappingRepository: WorkspaceToBoardMappingRepository,
    val boardRepository: BoardRepository,
    val boardToListMappingRepository: BoardToListMappingRepository,
    val listRepository: ListRepository
) {
    @PostMapping("/{workspaceId}")
    fun create(@PathVariable workspaceId: Int, @RequestBody body: BoardCreateRequest) {
        // TODO("check workspace accessibility")
        boardRepository.save(body.asEntity)
    }

    @GetMapping("/info/{boardId}")
    fun readInfo(@PathVariable boardId: Int): BoardInfoResponse {
        // TODO("check board accessibility")
        return boardRepository.getById(boardId).asResponse
    }

    @GetMapping("/{boardId}")
    fun read(@PathVariable boardId: Int): List<Pair<Int, ListInfoResponse>> {
        // TODO("check board accessibility")
        return boardToListMappingRepository.findBoardToListMappingEntitiesByBoardId(boardId).map {
            it.listId to listRepository.findByIdOrNull(it.listId)!!.asListInfoResponse
        }
    }

    @GetMapping("/edit/{boardId}")
    fun update(@PathVariable boardId: Int, @RequestBody body: BoardUpdateRequest) {
        // TODO("check board accessibility")
        val enitty = boardRepository.findByIdOrNull(boardId)!!
        if (body.name != null) enitty.name = body.name
        if (body.description != null) enitty.description = body.description
        boardRepository.save(enitty)
    }

    @GetMapping("/delete/{boardId}")
    fun delete(@PathVariable boardId: Int) {
        // TODO("check board accessibility")
        boardRepository.deleteById(boardId)
        // TODO("delete mappings and nested structures")
    }
}
