package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.CardEntity
import java.time.LocalDate

data class CardResponse(
    var id: Int? = null,
    var name: String? = null,
    var color: Int? = null,
    var creationData: LocalDate? = null,
    var deadline: LocalDate? = null,
    var creatorId: Int? = null,
    var cardContent: String? = null
)

val CardEntity.asResponse: CardResponse
    get() = CardResponse(
        id,
        name,
        color,
        creationData,
        deadline,
        creatorId,
        cardContent
    )
