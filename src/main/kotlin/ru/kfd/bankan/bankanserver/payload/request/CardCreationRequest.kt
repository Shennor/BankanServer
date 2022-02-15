package ru.kfd.bankan.bankanserver.payload.request

import ru.kfd.bankan.bankanserver.entity.CardEntity
import java.time.LocalDate

data class CardCreationRequest(
    var name: String,
    var color: Int? = null,
    var deadline: LocalDate? = null,
    var cardContent: String = "{}"
)

fun CardCreationRequest.asEntity(creatorId: Int) = CardEntity(
    name = name,
    color = color,
    deadline = deadline,
    creatorId = creatorId
)
