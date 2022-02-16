package ru.kfd.bankan.bankanserver.controller.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.controller.safeFindById
import ru.kfd.bankan.bankanserver.payload.request.SettingsPatchRequest
import ru.kfd.bankan.bankanserver.payload.response.SettingsResponse
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
    fun getSettings(@PathVariable userId: Int): SettingsResponse {
        // check existence
        userInfoRepository.safeFindById(userId)
        // check permissions
        allowedTo.editUserInfo(userId)
        // read settings
        return userSettingsRepository.safeFindById(userId).asResponse
    }

    @PatchMapping("/{userId}")
    fun updateSettings(@PathVariable userId: Int, @RequestBody request: SettingsPatchRequest) {
        // check existence
        userInfoRepository.safeFindById(userId)
        // check permissions
        allowedTo.editUserInfo(userId)
        // change
        val settingsEntity = userSettingsRepository.safeFindById(userId)
        settingsEntity.settings = request
        userSettingsRepository.save(settingsEntity)
    }
}