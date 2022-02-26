package ru.kfd.bankan.bankanserver.payload.request

import ru.kfd.bankan.bankanserver.entity.BoardEntity
import java.time.LocalDate

data class BoardCreateRequest(
    val name: String,
    val description: String
)

val BoardCreateRequest.asEntity
    get() = BoardEntity().also {
        it.name = name
        it.description = name
    }
