package ru.kfd.bankan.bankanserver.controller.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.payload.request.SettingsPatchRequest
import ru.kfd.bankan.bankanserver.payload.response.asResponse
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository
import ru.kfd.bankan.bankanserver.repository.UserSettingsRepository

@RequestMapping("/api/settings")
@RestController
class Settings(
    val userSettingsRepository: UserSettingsRepository,
    val userInfoRepository: UserInfoRepository,
    val allowedTo: AllowedTo,
    val mapper: ObjectMapper
) {

    @GetMapping("/{userId}")
    fun getSettings(@PathVariable userId: Int): ResponseEntity<String> {
        // check existence
        val userOptional = userInfoRepository.findById(userId)
        if (userOptional.isEmpty) return ResponseEntity("There is no user with id $userId", HttpStatus.NOT_FOUND)
        // check permissions
        val optional = allowedTo.editUserInfo(userId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get()) return ResponseEntity("You have no permissions to do this", HttpStatus.FORBIDDEN)
        // read settings
        val settingsEntity = userSettingsRepository.findByUserId(userId)
            ?: return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        return ResponseEntity(mapper.writeValueAsString(settingsEntity.asResponse), HttpStatus.OK)
    }

    @PatchMapping("/{userId}")
    fun updateSettings(@PathVariable userId: Int, @RequestBody request: SettingsPatchRequest): ResponseEntity<String> {
        // check existence
        val userOptional = userInfoRepository.findById(userId)
        if (userOptional.isEmpty) return ResponseEntity("There is no user with id $userId", HttpStatus.NOT_FOUND)
        // check permissions
        val optional = allowedTo.editUserInfo(userId)
        if (optional.isEmpty) return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if (!optional.get()) return ResponseEntity("You have no permissions to do this", HttpStatus.FORBIDDEN)
        // change
        val settingsEntity = userSettingsRepository.findByUserId(userId)
            ?: return ResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        if(request.settings != null) settingsEntity.settings = request.settings
        userSettingsRepository.save(settingsEntity)
        return ResponseEntity("Settings changed", HttpStatus.OK)
    }
}