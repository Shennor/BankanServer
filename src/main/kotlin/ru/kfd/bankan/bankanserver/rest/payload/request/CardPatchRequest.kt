package ru.kfd.bankan.bankanserver.rest.payload.request

import java.time.LocalDate

data class CardPatchRequest(
    var name: String? = null,
    var changeColor: Boolean = false,
    var color: Int? = null,
    var changeDeadline: Boolean = false,
    var deadline: LocalDate? = null,
    var cardContent: String? = null
)
