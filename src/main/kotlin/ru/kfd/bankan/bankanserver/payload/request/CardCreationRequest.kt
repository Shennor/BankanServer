package ru.kfd.bankan.bankanserver.payload.request

import ru.kfd.bankan.bankanserver.entity.UserInfoEntity
import java.time.LocalDate

data class CardCreationRequest (
    var name: String,
    var color: Int? = null,
    var deadline: LocalDate? = null,
    var cardContent: String = "{}"
)