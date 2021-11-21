package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.UserInfo
import ru.kfd.bankan.bankanserver.UserInfoRepository

@RestController
@RequestMapping("api/user")
class Users(
    val userInfoRepository: UserInfoRepository
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): UserInfo {
        return userInfoRepository.findById(id).get()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun post(@RequestBody(required = true) user: UserInfo): String {
        val newUser = userInfoRepository.save(user)
        return "Added $newUser"
    }
}
