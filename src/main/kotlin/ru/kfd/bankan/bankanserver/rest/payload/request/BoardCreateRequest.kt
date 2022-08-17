package ru.kfd.bankan.bankanserver.rest.payload.request

import ru.kfd.bankan.bankanserver.database.entity.BoardEntity
import java.sql.Date
import java.time.LocalDate

data class BoardCreateRequest(
    val name: String,
    val description: String
)

val BoardCreateRequest.asEntity
    get() = BoardEntity().also {
        it.name = name
        it.description = description
        it.creationData = Date.valueOf(LocalDate.now())
    }
