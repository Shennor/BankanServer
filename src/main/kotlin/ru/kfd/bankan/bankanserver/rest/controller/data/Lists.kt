package ru.kfd.bankan.bankanserver.rest.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.database.entity.BoardToListMappingEntity
import ru.kfd.bankan.bankanserver.database.repository.BoardToListMappingRepository
import ru.kfd.bankan.bankanserver.database.repository.CardRepository
import ru.kfd.bankan.bankanserver.database.repository.ListRepository
import ru.kfd.bankan.bankanserver.database.repository.ListToCardMappingRepository
import ru.kfd.bankan.bankanserver.rest.controller.AllowedTo
import ru.kfd.bankan.bankanserver.rest.controller.safeFindById
import ru.kfd.bankan.bankanserver.rest.controller.updateWithIfNotNull
import ru.kfd.bankan.bankanserver.rest.payload.request.ListPatchRequest
import ru.kfd.bankan.bankanserver.rest.payload.request.ListRequest
import ru.kfd.bankan.bankanserver.rest.payload.request.asEntity
import ru.kfd.bankan.bankanserver.rest.payload.response.ListContentResponse
import ru.kfd.bankan.bankanserver.rest.payload.response.ListInfoResponse
import ru.kfd.bankan.bankanserver.rest.payload.response.asListInfoResponse
import ru.kfd.bankan.bankanserver.rest.payload.response.asResponse

@RestController
@RequestMapping("/api/list/")
class Lists(
    private val listRepository: ListRepository,
    private val listToCardMappingRepository: ListToCardMappingRepository,
    private val boardToListMappingRepository: BoardToListMappingRepository,
    private val cardRepository: CardRepository,
    private val allowedTo: AllowedTo
) {
    @PostMapping("/{boardId}")
    fun create(@PathVariable boardId: Int, @RequestBody listRequest: ListRequest) {
        allowedTo.writeByBoardId(boardId)
        val list = listRepository.save(listRequest.asEntity)
        val mapping = BoardToListMappingEntity(
            boardId = boardId,
            listId = list.id,
            indexOfListInBoard = boardToListMappingRepository.countBoardToListMappingEntitiesByBoardId(boardId)
        )
        boardToListMappingRepository.save(mapping)
    }

    // TODO: Test
    @GetMapping("/info/{listId}")
    fun readInfo(@PathVariable listId: Int): ListInfoResponse {
        allowedTo.readByListId(listId)
        return listRepository.safeFindById(listId).asListInfoResponse
    }

    @GetMapping("/{id}")
    fun readListContent(@PathVariable id: Int): ListContentResponse {
        allowedTo.readByListId(id)
        return ListContentResponse(listToCardMappingRepository.getAllByListId(id).map {
            cardRepository.safeFindById(it.cardId).asResponse
        })
    }

    @PatchMapping("/edit/{id}")
    fun updateList(
        @PathVariable id: Int,
        @RequestBody patch: ListPatchRequest
    ) {
        allowedTo.writeByListId(id)
        val old = listRepository.safeFindById(id)
        old updateWithIfNotNull patch
//        if (patch.name != null) old.name = patch.name
//        if (patch.description != null) old.description = patch.description
        listRepository.save(old)
    }

    //TODO: Test
    @DeleteMapping("/{boardId}/{listId}")
    fun deleteList(@PathVariable boardId: Int, @PathVariable listId: Int) {
        // TODO: delete only 1 mapping
    }

    //TODO: Test
    @DeleteMapping("/{id}")
    fun deleteList(@PathVariable id: Int) {
        allowedTo.writeByListId(id)
        listRepository.deleteById(id)
        // TODO: add deletion from all boards
    }

}


