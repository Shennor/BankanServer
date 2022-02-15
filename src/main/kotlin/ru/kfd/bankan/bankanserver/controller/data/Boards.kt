package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.payload.request.BoardCreateRequest
import ru.kfd.bankan.bankanserver.repository.BoardRepository
import ru.kfd.bankan.bankanserver.repository.BoardToListMappingRepository


// TODO
@RequestMapping("/api/board/")
@RestController
class Boards(
    val boardRepository: BoardRepository,
    val boardToListMappingRepository: BoardToListMappingRepository
) {
    @PostMapping("/{workspaceId}")
    fun create(@PathVariable workspaceId: Int, @RequestBody body: BoardCreateRequest) {
        //TODO("check workspace accessibility")
        //boardRepository.save(body.asEntity)
    }
}
