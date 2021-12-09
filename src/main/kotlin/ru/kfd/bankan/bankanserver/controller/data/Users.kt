package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.entity.UserInfo
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository

@RestController
@RequestMapping("api/user")
class Users(
    val userInfoRepository: UserInfoRepository
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): UserInfo {
        return userInfoRepository.findById(id).get()
    }

    @PostMapping
    fun post(@RequestBody(required = true) user: UserInfo): String {
        val newUser = userInfoRepository.save(user)
        return "Added $newUser"
    }

    @PatchMapping
    fun patch(
        @RequestBody(required = true) user: UserInfo,
    ): String {
        val old = userInfoRepository.findById(user.id).get().copy()
        val newUser = userInfoRepository.save(user)
        return "Updated \nold: $old\nnew: $newUser"
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): String {
        val user = userInfoRepository.findById(id).get()
        userInfoRepository.deleteById(id)
        return "Ok, $user was deleted"
    }
}
