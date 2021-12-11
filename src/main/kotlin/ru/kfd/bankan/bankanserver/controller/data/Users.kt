package ru.kfd.bankan.bankanserver.controller.data

/*
@RestController
@RequestMapping("api/user")
class Users(
    val authInfoRepository: AuthInfoRepository
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): UserInfo {
        return authInfoRepository.findById(id).get()
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
*/