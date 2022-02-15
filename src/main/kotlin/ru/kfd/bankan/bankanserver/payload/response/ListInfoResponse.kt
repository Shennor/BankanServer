package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.ListEntity

data class ListInfoResponse(
    val name: String,
    val description: String,
)

val ListEntity.asListInfoResponse
    get() = ListInfoResponse(name!!, description!!)
