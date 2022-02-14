package ru.kfd.bankan.bankanserver.payload.request

import java.time.LocalDate

data class CardEditionRequest(
    var name: String? = null,
    var changeColor: Boolean = false,
    var color: Int? = null,
    var changeDeadline: Boolean = false,
    var deadline: LocalDate? = null,
    var cardContent: String? = null
)
