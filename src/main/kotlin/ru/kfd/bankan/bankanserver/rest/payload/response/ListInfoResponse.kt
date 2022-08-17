package ru.kfd.bankan.bankanserver.rest.payload.response

import ru.kfd.bankan.bankanserver.database.entity.ListEntity

data class ListInfoResponse(
    val name: String,
    val description: String,
)

val ListEntity.asListInfoResponse
    get() = ListInfoResponse(name!!, description!!)
