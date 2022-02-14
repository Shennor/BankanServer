package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kfd.bankan.bankanserver.repository.BoardRepository
import ru.kfd.bankan.bankanserver.repository.BoardToListMappingRepository


// TODO
@RequestMapping("/api/board")
@RestController
class Boards(
    val boardRepository: BoardRepository,
    val boardToListMappingRepository: BoardToListMappingRepository
) {
}
