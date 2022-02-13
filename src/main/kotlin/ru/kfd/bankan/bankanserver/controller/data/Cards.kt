package ru.kfd.bankan.bankanserver.controller.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.payload.response.CardResponse
import ru.kfd.bankan.bankanserver.repository.CardRepository
import org.springframework.security.core.context.SecurityContextHolder
import ru.kfd.bankan.bankanserver.entity.CardEntity
import ru.kfd.bankan.bankanserver.entity.ListToCardMappingEntity
import ru.kfd.bankan.bankanserver.payload.request.CardCreationRequest
import ru.kfd.bankan.bankanserver.payload.request.CardEditionRequest
import ru.kfd.bankan.bankanserver.repository.AuthInfoRepository
import ru.kfd.bankan.bankanserver.repository.ListRepository
import java.time.LocalDate
import ru.kfd.bankan.bankanserver.repository.ListToCardMappingRepository as ListToCardMappingRepository

@RestController
@RequestMapping("/api/card")
class Cards(val cardRepository : CardRepository,
            val listRepository: ListRepository,
            val authInfoRepository: AuthInfoRepository,
            val listToCardMappingRepository: ListToCardMappingRepository,
            val mapper : ObjectMapper){

    // available for who?
    @GetMapping("/{cardId}")
    fun getCard(@PathVariable cardId : Int): ResponseEntity<String> {
        // check if we are allowed to get a card
        // TODO

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
        // check if creator have permission to create a card
        if(!SecurityContextHolder.getContext().authentication.isAuthenticated) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val login = SecurityContextHolder.getContext().authentication.principal

        val creatorEntity = authInfoRepository.findByEmail(login.toString())
            ?: return ResponseEntity("Something is wrong.", HttpStatus.INTERNAL_SERVER_ERROR)
        // check if listId exists
        if (!listRepository.existsById(listId)) return ResponseEntity("There is no list with id $listId", HttpStatus.NOT_FOUND)
        // creating a card
        val cardEntity = CardEntity()
        cardEntity.name = requestBody.name
        cardEntity.color = requestBody.color
        cardEntity.creationData = LocalDate.now()
        cardEntity.deadline = requestBody.deadline
        cardEntity.creatorId = creatorEntity.userId
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

    // available for who?
    @PatchMapping("/edit/{cardId}")
    fun editCard(@PathVariable cardId: Int, @RequestBody requestBody: CardEditionRequest) : ResponseEntity<String>{
        val optional = cardRepository.findById(cardId)
        if(optional.isEmpty) return ResponseEntity("Card with id $cardId does not exist", HttpStatus.NOT_FOUND)
        val cardEntity = optional.get()
        if (requestBody.name != null) cardEntity.name = requestBody.name
        if (requestBody.cardContent != null) cardEntity.cardContent = requestBody.cardContent
        if (requestBody.changeColor) cardEntity.color = requestBody.color
        if (requestBody.changeDeadline) cardEntity.deadline = requestBody.deadline
        return ResponseEntity("Card $cardId edited", HttpStatus.OK)
    }

    @DeleteMapping("/{cardId}")
    fun deleteCard(@PathVariable cardId : Int) : ResponseEntity<String>{
        try {
            cardRepository.deleteById(cardId)
        } catch (e : Exception){
            return ResponseEntity("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity("Card with id $cardId deleted", HttpStatus.OK)
    }

}
