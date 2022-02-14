package ru.kfd.bankan.bankanserver.controller.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.payload.response.CardResponse
import org.springframework.security.core.context.SecurityContextHolder
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.entity.CardEntity
import ru.kfd.bankan.bankanserver.entity.ListToCardMappingEntity
import ru.kfd.bankan.bankanserver.payload.request.CardCreationRequest
import ru.kfd.bankan.bankanserver.payload.request.CardEditionRequest
import ru.kfd.bankan.bankanserver.repository.*
import ru.kfd.bankan.bankanserver.repository.ListToCardMappingRepository as ListToCardMappingRepository

@RestController
@RequestMapping("/api/card")
class Cards(val cardRepository : CardRepository,
            val listRepository: ListRepository,
            val authInfoRepository: AuthInfoRepository,
            val listToCardMappingRepository: ListToCardMappingRepository,
            val allowedTo: AllowedTo,
            val mapper : ObjectMapper){

    // available for who?
    @GetMapping("/{cardId}")
    fun getCard(@PathVariable cardId : Int): ResponseEntity<String> {
        // check if card exists
        val optionalCard = cardRepository.findById(cardId)
        if(optionalCard.isEmpty) return ResponseEntity("Card with id $cardId does not exist", HttpStatus.NOT_FOUND)
        // check if user have permission to get this card
        val optionalBool = allowedTo.readByCardId(cardId)
        if (optionalBool.isEmpty) return ResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optionalBool.get())
            return ResponseEntity("You have no permissions to read this card", HttpStatus.FORBIDDEN)
        // get card
        val optional = cardRepository.findById(cardId)
        if(optional.isEmpty)
            return ResponseEntity<String>("This id isn't in database", HttpStatus.NOT_FOUND)
        val cardEntity = optional.get()
        return ResponseEntity(mapper.writeValueAsString(
            CardResponse(
            cardEntity.id,
            cardEntity.name,
            cardEntity.color,
            cardEntity.creationData,
            cardEntity.deadline,
            cardEntity.creatorId,
            cardEntity.cardContent)), HttpStatus.OK);
    }

    @PostMapping("/{listId}")
    fun createCard(@PathVariable listId : Int, @RequestBody requestBody: CardCreationRequest) : ResponseEntity<String>{
        // check if listId exists
        if (!listRepository.existsById(listId)) return ResponseEntity("There is no list with id $listId", HttpStatus.NOT_FOUND)
        // check if creator have permission to create a card
        if (!SecurityContextHolder.getContext().authentication.isAuthenticated) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val optional = allowedTo.writeByListId(listId);
        if (optional.isEmpty) return ResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get())
            return ResponseEntity("You have no permissions to create a card in this list", HttpStatus.FORBIDDEN)
        // creating a card
        val cardEntity = CardEntity()
        cardEntity.name = requestBody.name
        cardEntity.color = requestBody.color
        cardEntity.deadline = requestBody.deadline
        cardEntity.creatorId = authInfoRepository.findByEmail(
            SecurityContextHolder.getContext().authentication.principal.toString())!!.userId
        val entity = cardRepository.save(cardEntity)
        // add list to card mapping (adding card to the end of the list)
        val mapping = ListToCardMappingEntity();
        mapping.cardId = entity.id!!
        mapping.listId = listId
        val listOfMapping = listToCardMappingRepository.getAllByListId(listId)
        mapping.indexOfCardInList = listOfMapping.size
        listToCardMappingRepository.save(mapping)
        return ResponseEntity("Card created with id ${entity.id}", HttpStatus.OK)
    }

    // add copy changing

    @PatchMapping("/edit/{cardId}")
    fun editCard(@PathVariable cardId: Int, @RequestBody requestBody: CardEditionRequest) : ResponseEntity<String>{
        // check if card exists
        val optionalCard = cardRepository.findById(cardId)
        if(optionalCard.isEmpty) return ResponseEntity("Card with id $cardId does not exist", HttpStatus.NOT_FOUND)
        // check if user have permission to change this card
        val optionalBool = allowedTo.writeByCardId(cardId);
        if (optionalBool.isEmpty) return ResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optionalBool.get())
            return ResponseEntity("You have no permissions to edit this card", HttpStatus.FORBIDDEN)
        // change the card
        val cardEntity = optionalCard.get()
        if (requestBody.name != null) cardEntity.name = requestBody.name
        if (requestBody.cardContent != null) cardEntity.cardContent = requestBody.cardContent
        if (requestBody.changeColor) cardEntity.color = requestBody.color
        if (requestBody.changeDeadline) cardEntity.deadline = requestBody.deadline
        return ResponseEntity("Card $cardId edited", HttpStatus.OK)
    }

    @DeleteMapping("/{listId}/{cardId}")
    fun deleteCardFromList(@PathVariable listId : Int, @PathVariable cardId : Int) : ResponseEntity<String>{
        // check if card exists
        if(cardRepository.findById(cardId).isEmpty)
            return ResponseEntity("Card with id $cardId does not exist", HttpStatus.NOT_FOUND)
        // check if card is not in this list
        if(!listToCardMappingRepository.existsByListIdAndCardId(listId, cardId))
            return ResponseEntity("Card with id $cardId is not in the list with id $listId", HttpStatus.NOT_FOUND)
        // check if user have permission to change this card in the list
        val optional = allowedTo.writeByListId(listId);
        if (optional.isEmpty) return ResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get())
            return ResponseEntity("You have no permissions to delete this card from this list", HttpStatus.FORBIDDEN)
        try {
            // delete mapping of this card and this list
            listToCardMappingRepository.deleteByListIdAndCardId(listId, cardId)
        } catch (e : Exception){
            return ResponseEntity("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity("Card with id $cardId deleted", HttpStatus.OK)
    }

    @DeleteMapping("/{cardId}")
    fun deleteCard(@PathVariable cardId : Int) : ResponseEntity<String>{
        // check if card exists
        if(cardRepository.findById(cardId).isEmpty) return ResponseEntity("Card with id $cardId does not exist", HttpStatus.NOT_FOUND)
        // check if user have permission to delete this card in all lists
        val optional = allowedTo.writeByCardId(cardId);
        if (optional.isEmpty) return ResponseEntity("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get())
            return ResponseEntity("You have no permissions to delete this card", HttpStatus.FORBIDDEN)
        try {
            // delete all mappings with this card
            listToCardMappingRepository.deleteAllByCardId(cardId)
            // delete the card
            cardRepository.deleteById(cardId)
        } catch (e : Exception){
            return ResponseEntity("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity("Card with id $cardId deleted", HttpStatus.OK)
    }

}
