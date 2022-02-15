package ru.kfd.bankan.bankanserver.controller.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.controller.safeFindById
import ru.kfd.bankan.bankanserver.entity.ListToCardMappingEntity
import ru.kfd.bankan.bankanserver.payload.request.CardCreationRequest
import ru.kfd.bankan.bankanserver.payload.request.CardPatchRequest
import ru.kfd.bankan.bankanserver.payload.request.asEntity
import ru.kfd.bankan.bankanserver.payload.response.asResponse
import ru.kfd.bankan.bankanserver.repository.AuthInfoRepository
import ru.kfd.bankan.bankanserver.repository.CardRepository
import ru.kfd.bankan.bankanserver.repository.ListRepository
import ru.kfd.bankan.bankanserver.repository.ListToCardMappingRepository

@RestController
@RequestMapping("/api/card")
class Cards(
    val cardRepository: CardRepository,
    val listRepository: ListRepository,
    val authInfoRepository: AuthInfoRepository,
    val listToCardMappingRepository: ListToCardMappingRepository,
    val allowedTo: AllowedTo,
    val mapper: ObjectMapper
) {

    // available for who?
    @GetMapping("/{cardId}")
    fun getCard(@PathVariable cardId: Int): ResponseEntity<String> {
        // check if card exists and get
        val cardEntity = cardRepository.safeFindById(cardId)

        // check if user have permission to get this card
        val optionalBool = allowedTo.readByCardId(cardId)
        if (optionalBool.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optionalBool.get())
            return ResponseEntity("You have no permissions to read this card", HttpStatus.FORBIDDEN)

        return ResponseEntity(mapper.writeValueAsString(cardEntity.asResponse), HttpStatus.OK)
    }

    @PostMapping("/{listId}")
    fun createCard(@PathVariable listId: Int, @RequestBody requestBody: CardCreationRequest): ResponseEntity<String> {
        // check if listId exists
        listRepository.safeFindById(listId)

        // check if creator have permission to create a card
        if (!SecurityContextHolder.getContext().authentication.isAuthenticated) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val optional = allowedTo.writeByListId(listId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get())
            return ResponseEntity("You have no permissions to create a card in this list", HttpStatus.FORBIDDEN)
        // creating a card
        val cardEntity =
            requestBody.asEntity(authInfoRepository.findByEmail(SecurityContextHolder.getContext().authentication.principal.toString())!!.userId)

        val entity = cardRepository.save(cardEntity)
        // add list to card mapping (adding card to the end of the list)

        val mapping = ListToCardMappingEntity(
            cardId = entity.id,
            listId = listId,
            indexOfCardInList = listToCardMappingRepository.countListToCardMappingEntitiesByListId(listId)
        )

        listToCardMappingRepository.save(mapping)
        return ResponseEntity("Card created with id ${entity.id}", HttpStatus.OK)
    }

    // add copy changing

    @PatchMapping("/edit/{cardId}")
    fun editCard(@PathVariable cardId: Int, @RequestBody requestBody: CardPatchRequest): ResponseEntity<String> {
        // check if card exists and get
        val cardEntity = cardRepository.safeFindById(cardId)
        // check if user have permission to change this card
        val optionalBool = allowedTo.writeByCardId(cardId)
        if (optionalBool.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optionalBool.get())
            return ResponseEntity("You have no permissions to edit this card", HttpStatus.FORBIDDEN)
        // change the card
        if (requestBody.name != null) cardEntity.name = requestBody.name
        if (requestBody.cardContent != null) cardEntity.cardContent = requestBody.cardContent
        if (requestBody.changeColor) cardEntity.color = requestBody.color
        if (requestBody.changeDeadline) cardEntity.deadline = requestBody.deadline
        cardRepository.save(cardEntity)
        return ResponseEntity("Card $cardId edited", HttpStatus.OK)
    }

    @DeleteMapping("/{listId}/{cardId}")
    fun deleteCardFromList(@PathVariable listId: Int, @PathVariable cardId: Int): ResponseEntity<String> {
        // check if card exists
        cardRepository.safeFindById(cardId)

        // check if card is not in this list
        if (!listToCardMappingRepository.existsByListIdAndCardId(listId, cardId))
            return ResponseEntity("Card with id $cardId is not in the list with id $listId", HttpStatus.NOT_FOUND)
        // check if user have permission to change this card in the list
        val optional = allowedTo.writeByListId(listId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get())
            return ResponseEntity("You have no permissions to delete this card from this list", HttpStatus.FORBIDDEN)
        try {
            // delete mapping of this card and this list
            listToCardMappingRepository.deleteByListIdAndCardId(listId, cardId)
        } catch (e: Exception) {
            return ResponseEntity("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity("Card with id $cardId deleted", HttpStatus.OK)
    }

    @DeleteMapping("/{cardId}")
    fun deleteCard(@PathVariable cardId: Int): ResponseEntity<String> {
        // check if card exists
        cardRepository.safeFindById(cardId)

        // check if user have permission to delete this card in all lists
        val optional = allowedTo.writeByCardId(cardId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get())
            return ResponseEntity("You have no permissions to delete this card", HttpStatus.FORBIDDEN)
        try {
            // delete all mappings with this card
            listToCardMappingRepository.deleteAllByCardId(cardId)
            // delete the card
            cardRepository.deleteById(cardId)
        } catch (e: Exception) {
            return ResponseEntity("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity("Card with id $cardId deleted", HttpStatus.OK)
    }

}
