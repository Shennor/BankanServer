package ru.kfd.bankan.bankanserver.rest.payload.response

import ru.kfd.bankan.bankanserver.database.entity.UserInfoEntity
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

