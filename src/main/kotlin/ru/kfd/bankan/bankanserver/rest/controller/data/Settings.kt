package ru.kfd.bankan.bankanserver.rest.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.database.repository.UserInfoRepository
import ru.kfd.bankan.bankanserver.database.repository.UserSettingsRepository
import ru.kfd.bankan.bankanserver.rest.controller.AllowedTo
import ru.kfd.bankan.bankanserver.rest.controller.safeFindById
import ru.kfd.bankan.bankanserver.rest.payload.request.SettingsPatchRequest
import ru.kfd.bankan.bankanserver.rest.payload.response.SettingsResponse
import ru.kfd.bankan.bankanserver.rest.payload.response.asResponse

@RequestMapping("/api/settings")
@RestController
class Settings(
    private val userSettingsRepository: UserSettingsRepository,
    private val userInfoRepository: UserInfoRepository,
    private val allowedTo: AllowedTo,
) {
    // TODO: Test
    @GetMapping("/{userId}")
    fun getSettings(@PathVariable userId: Int): SettingsResponse {
        // check existence
        userInfoRepository.safeFindById(userId)
        // check permissions
        allowedTo.editUserInfo(userId)
        // read settings
        return userSettingsRepository.safeFindById(userId).asResponse
    }

    // TODO: Test
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