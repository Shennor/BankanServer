package ru.kfd.bankan.bankanserver.payload.request

import ru.kfd.bankan.bankanserver.entity.ListEntity
import java.sql.Date
import java.time.LocalDate

data class ListRequest(
    val name: String,
    val description: String
)

val ListRequest.asEntity
    get() = ListEntity(name = name, description = description, creationData = Date.valueOf(LocalDate.now()))
