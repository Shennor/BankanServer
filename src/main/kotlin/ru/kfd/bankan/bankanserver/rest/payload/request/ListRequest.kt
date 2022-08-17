package ru.kfd.bankan.bankanserver.rest.payload.request

import ru.kfd.bankan.bankanserver.database.entity.ListEntity
import java.sql.Date
import java.time.LocalDate

// TODO: add exceptions on length of name and description
data class ListRequest(
    val name: String,
    val description: String
)

val ListRequest.asEntity
    get() = ListEntity(name = name, description = description, creationData = Date.valueOf(LocalDate.now()))
