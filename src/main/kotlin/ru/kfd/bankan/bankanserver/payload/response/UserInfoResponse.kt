package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.UserInfoEntity
import java.time.LocalDate

data class UserInfoResponse(
    val id: Int? = null,
    val name: String? = null,
    val registrationDate: LocalDate? = null
)

val UserInfoEntity.asResponse: UserInfoResponse
    get() = UserInfoResponse(
        id,
        name,
        registrationDate
    )

