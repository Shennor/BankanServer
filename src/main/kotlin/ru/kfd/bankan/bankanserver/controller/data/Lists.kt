package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.safeFindById
import ru.kfd.bankan.bankanserver.controller.updateWithIfNotNull
import ru.kfd.bankan.bankanserver.entity.BoardToListMappingEntity
import ru.kfd.bankan.bankanserver.payload.request.ListPatchRequest
import ru.kfd.bankan.bankanserver.payload.request.ListRequest
import ru.kfd.bankan.bankanserver.payload.request.asEntity
import ru.kfd.bankan.bankanserver.payload.response.ListContentResponse
import ru.kfd.bankan.bankanserver.payload.response.ListInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.asListInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.asResponse
import ru.kfd.bankan.bankanserver.repository.BoardToListMappingRepository
import ru.kfd.bankan.bankanserver.repository.CardRepository
import ru.kfd.bankan.bankanserver.repository.ListRepository
import ru.kfd.bankan.bankanserver.repository.ListToCardMappingRepository

@RestController
@RequestMapping("/api/list/")
class Lists(
    val listRepository: ListRepository,
    val listToCardMappingRepository: ListToCardMappingRepository,
    val boardToListMappingRepository: BoardToListMappingRepository,
    val cardRepository: CardRepository
) {
    @PostMapping("/{boardId}")
    fun create(@PathVariable boardId: Int, @RequestBody listRequest: ListRequest) {
        val list = listRepository.save(listRequest.asEntity)
        val mapping = BoardToListMappingEntity(
            boardId = boardId,
            listId = list.id,
            indexOfListInBoard = boardToListMappingRepository.countBoardToListMappingEntitiesByBoardId(boardId)
        )
        boardToListMappingRepository.save(mapping)
    }

    @GetMapping("/info/{id}")
    fun readInfo(@PathVariable id: Int): ListInfoResponse {
        return listRepository.safeFindById(id).asListInfoResponse
    }

    @GetMapping("/{id}")
    fun readListContent(@PathVariable id: Int): ListContentResponse {
        return ListContentResponse(listToCardMappingRepository.getAllByListId(id).map {
            cardRepository.safeFindById(it.cardId).asResponse
        })
    }

    @PatchMapping("/edit/{id}")
    fun updateListProperty(
        @PathVariable id: Int,
        @RequestBody patch: ListPatchRequest
    ) {
        val old = listRepository.safeFindById(id)
        old updateWithIfNotNull patch
//        if (patch.name != null) old.name = patch.name
//        if (patch.description != null) old.description = patch.description
        listRepository.save(old)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        listRepository.deleteById(id)
    }
}


