package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.entity.BoardToListMappingEntity
import ru.kfd.bankan.bankanserver.payload.request.ListPatchRequest
import ru.kfd.bankan.bankanserver.payload.request.ListRequest
import ru.kfd.bankan.bankanserver.payload.request.asEntity
import ru.kfd.bankan.bankanserver.payload.response.ListContentResponse
import ru.kfd.bankan.bankanserver.payload.response.ListInfoResponse
import ru.kfd.bankan.bankanserver.payload.response.asResponse
import ru.kfd.bankan.bankanserver.repository.BoardToListMappingRepository
import ru.kfd.bankan.bankanserver.repository.CardRepository
import ru.kfd.bankan.bankanserver.repository.ListRepository
import ru.kfd.bankan.bankanserver.repository.ListToCardMappingRepository

@RestController
@RequestMapping("api/list/")
class Lists(
    val listRepository: ListRepository,
    val listToCardMappingRepository: ListToCardMappingRepository,
    val boardToListMappingRepository: BoardToListMappingRepository,
    val cardRepository: CardRepository
) {
    @PostMapping("{boardId}")
    fun create(@PathVariable boardId: Int, @RequestBody listRequest: ListRequest) {
        val list = listRepository.save(listRequest.asEntity)
        val mapping = BoardToListMappingEntity()
        mapping.boardId = boardId
        mapping.listId = list.id!!
        boardToListMappingRepository.save(mapping)
    }

    @GetMapping("info/{id}")
    fun readInfo(@PathVariable id: Int) =
        listRepository.findByIdOrNull(id)?.run {
            ListInfoResponse(name!!, description!!)
        }

    @GetMapping("{id}")
    fun readListContent(@PathVariable id: Int) =
        ListContentResponse(listToCardMappingRepository.getAllByListId(id).map {
            cardRepository.findByIdOrNull(it.cardId)!!.asResponse
        })

    @PatchMapping("{id}/edit/{property}")
    fun updateListProperty(
        @PathVariable id: Int,
        @PathVariable property: String,
        @RequestBody patch: ListPatchRequest
    ) {
        val old = listRepository.findByIdOrNull(id)
        if (old != null) {
            if (patch.name != null)
                old.name = patch.name
            if (patch.description != null)
                old.description = patch.description
            listRepository.save(old)
        }
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Int) {
        listRepository.deleteById(id)
    }
}


