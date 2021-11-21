package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.entities.Board
import ru.kfd.bankan.bankanserver.repositories.BoardToListsMappingRepository
import ru.kfd.bankan.bankanserver.repositories.BoardsRepository


// TODO
@RequestMapping("/api/board/")
@RestController
class Boards(
    val boardToListsMappingRepository: BoardToListsMappingRepository,
    val boardsRepository: BoardsRepository
) {
    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Int): String {
        println(boardId)
        return boardToListsMappingRepository.findAllByBoard_Id(boardId)
            .toString()
    }

    @PostMapping("/", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postBoard(
        @RequestBody board: Board
    ): Board {
        return boardsRepository.save(board)
    }
}
