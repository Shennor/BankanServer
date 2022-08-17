package ru.kfd.bankan.bankanserver.rest.controller.data

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.database.entity.ListToCardMappingEntity
import ru.kfd.bankan.bankanserver.database.repository.CardRepository
import ru.kfd.bankan.bankanserver.database.repository.ListRepository
import ru.kfd.bankan.bankanserver.database.repository.ListToCardMappingRepository
import ru.kfd.bankan.bankanserver.rest.controller.AllowedTo
import ru.kfd.bankan.bankanserver.rest.controller.IdNotFoundException
import ru.kfd.bankan.bankanserver.rest.controller.safeFindById
import ru.kfd.bankan.bankanserver.rest.payload.request.CardCreationRequest
import ru.kfd.bankan.bankanserver.rest.payload.request.CardPatchRequest
import ru.kfd.bankan.bankanserver.rest.payload.request.asEntity
import ru.kfd.bankan.bankanserver.rest.payload.response.CardResponse
import ru.kfd.bankan.bankanserver.rest.payload.response.asResponse

@RestController
@RequestMapping("/api/card")
class Cards(
    private val cardRepository: CardRepository,
    private val listRepository: ListRepository,
    private val listToCardMappingRepository: ListToCardMappingRepository,
    private val allowedTo: AllowedTo,
) {

    // available for whom?
    @GetMapping("/{cardId}")
    fun getCard(@PathVariable cardId: Int): CardResponse {
        // check if card exists and get
        val cardEntity = cardRepository.safeFindById(cardId)

        // check if user have permission to get this card
        allowedTo.readByCardId(cardId)

        return cardEntity.asResponse
    }

    @PostMapping("/{listId}")
    fun createCard(@PathVariable listId: Int, @RequestBody requestBody: CardCreationRequest): ResponseEntity<String> {
        // check if listId exists
        listRepository.safeFindById(listId)
        // check if creator have permission to create a card
        allowedTo.writeByListId(listId)
        val userId = allowedTo.safeCurrentUser().userId
        // creating a card
        val cardEntity =
            requestBody.asEntity(userId)
        val entity = cardRepository.save(cardEntity)
        // add list to card mapping (adding card to the end of the list)
        val mapping = ListToCardMappingEntity(
            cardId = entity.id,
            listId = listId,
            indexOfCardInList = listToCardMappingRepository.countListToCardMappingEntitiesByListId(listId)
        )

        listToCardMappingRepository.save(mapping)
        // TODO("Change to normal response with id, but not this all")
        return ResponseEntity("Card created with id ${entity.id}", HttpStatus.OK)
    }

    // add copy changing

    @PatchMapping("/edit/{cardId}")
    fun editCard(@PathVariable cardId: Int, @RequestBody requestBody: CardPatchRequest) {
        // check if card exists and get
        val cardEntity = cardRepository.safeFindById(cardId)

        // check if user have permission to change this card
        allowedTo.writeByCardId(cardId)
        // change the card
        if (requestBody.name != null) cardEntity.name = requestBody.name
        if (requestBody.cardContent != null) cardEntity.cardContent = requestBody.cardContent
        if (requestBody.changeColor) cardEntity.color = requestBody.color
        if (requestBody.changeDeadline) cardEntity.deadline = requestBody.deadline
        cardRepository.save(cardEntity)
    }

    @DeleteMapping("/{listId}/{cardId}")
    fun deleteCardFromList(@PathVariable listId: Int, @PathVariable cardId: Int) {
        // check if card exists
        cardRepository.safeFindById(cardId)

        // check if card is not in this list
        if (!listToCardMappingRepository.existsByListIdAndCardId(listId, cardId))
            throw IdNotFoundException("Card with id $cardId is not in the list with id $listId")

        // check if user have permission to change this card in the list
        allowedTo.writeByListId(listId)

        cardRepository.deleteById(cardId)
    }

    @DeleteMapping("/{cardId}")
    fun deleteCard(@PathVariable cardId: Int) {
        // check if card exists
        cardRepository.safeFindById(cardId)
        // check if user have permission to delete this card in all lists
        allowedTo.writeByCardId(cardId)
        // delete all mappings with this card
        listToCardMappingRepository.deleteAllByCardId(cardId)
        // delete the card
        cardRepository.deleteById(cardId)
    }

}
