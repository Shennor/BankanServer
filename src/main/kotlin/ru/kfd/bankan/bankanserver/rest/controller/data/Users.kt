package ru.kfd.bankan.bankanserver.rest.controller.data

import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.database.repository.UserInfoRepository
import ru.kfd.bankan.bankanserver.rest.controller.AllowedTo
import ru.kfd.bankan.bankanserver.rest.controller.safeFindById
import ru.kfd.bankan.bankanserver.rest.payload.request.UserInfoPatchRequest
import ru.kfd.bankan.bankanserver.rest.payload.response.UserInfoResponse
import ru.kfd.bankan.bankanserver.rest.payload.response.asResponse

@RestController
@RequestMapping("api/user")
class Users(
    private val userInfoRepository: UserInfoRepository,
    private val allowedTo: AllowedTo,
) {
    // TODO: Test - strange problem with date
    @GetMapping("/{userId}")
    fun getUserInfo(@PathVariable userId: Int): UserInfoResponse {
        // check existing
        val userInfoEntity = userInfoRepository.safeFindById(userId)
        // this info is open for authenticated
        allowedTo.readUserInfo()
        // get user
        return userInfoEntity.asResponse
    }

    @PatchMapping("/{userId}")
    fun updateUserInfo(
        @PathVariable userId: Int,
        @RequestBody(required = true) request: UserInfoPatchRequest
    ) {
        // check existence
        val userEntity = userInfoRepository.safeFindById(userId)
        // check permissions
        allowedTo.editUserInfo(userId)
        // edit
        if (request.name != null) userEntity.name = request.name
        userInfoRepository.save(userEntity)
    }

    // TODO: Test
    @DeleteMapping("/{userId}")
    fun delete(@PathVariable userId: Int) {
        // check existing
        userInfoRepository.safeFindById(userId)
        // check permissions
        allowedTo.editUserInfo(userId)
        // delete
        userInfoRepository.deleteById(userId)
        // TODO("delete all information about user")
        // TODO("add user to waiting list")
    }
}
