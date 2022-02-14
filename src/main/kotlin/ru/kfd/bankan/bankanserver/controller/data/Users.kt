package ru.kfd.bankan.bankanserver.controller.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.payload.request.UserNameUpdateRequest
import ru.kfd.bankan.bankanserver.payload.response.asResponse
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository

@RestController
@RequestMapping("api/user")
class Users(
    val userInfoRepository: UserInfoRepository,
    val allowedTo: AllowedTo,
    val mapper: ObjectMapper
) {
    @GetMapping("/{userId}")
    fun getUserInfo(@PathVariable userId: Int): ResponseEntity<String> {
        // check existing
        val userInfoEntity = userInfoRepository.findById(userId)
        if (userInfoEntity.isEmpty) return ResponseEntity("There is no user with id $userId", HttpStatus.NOT_FOUND)
        // this info is open for authenticated
        if (!allowedTo.readUserInfo()) return ResponseEntity("You have no permissions to do this", HttpStatus.FORBIDDEN)
        // get user
        return ResponseEntity(mapper.writeValueAsString(userInfoEntity.get().asResponse), HttpStatus.OK)
    }

    @PatchMapping("/{userId}")
    fun updateUserInfo(
        @PathVariable userId: Int,
        @RequestBody(required = true) request: UserNameUpdateRequest
    ): ResponseEntity<String> {
        // check existence
        val userOptional = userInfoRepository.findById(userId)
        if (userOptional.isEmpty) return ResponseEntity("There is no user with id $userId", HttpStatus.NOT_FOUND)
        // check permissions
        val optional = allowedTo.editUserInfo(userId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if(!optional.get()) return ResponseEntity("You have no permissions to do this", HttpStatus.FORBIDDEN)
        // edit
        val userEntity = userOptional.get()
        if (request.name != null) userEntity.name = request.name
        userInfoRepository.save(userEntity)
        return ResponseEntity("User info updated", HttpStatus.OK)
    }

    @DeleteMapping("/{userId}")
    fun delete(@PathVariable userId: Int): ResponseEntity<String> {
        // check existing
        val userOptional = userInfoRepository.findById(userId)
        if(userOptional.isEmpty) return ResponseEntity("There is no user with id $userId", HttpStatus.NOT_FOUND)
        // check permissions
        val optional = allowedTo.editUserInfo(userId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if(!optional.get()) return ResponseEntity("You have no permissions to do this", HttpStatus.FORBIDDEN)
        // delete
        userInfoRepository.deleteById(userId)
        // TODO: delete all information about user
        // TODO: add user to waiting list
        return ResponseEntity("User deleted", HttpStatus.OK)
    }
}
