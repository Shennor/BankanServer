package ru.kfd.bankan.bankanserver.payload.request

import java.time.LocalDate

data class CardCreationRequest(
    var name: String,
    var color: Int? = null,
    var deadline: LocalDate? = null,
    var cardContent: String = "{}"
)