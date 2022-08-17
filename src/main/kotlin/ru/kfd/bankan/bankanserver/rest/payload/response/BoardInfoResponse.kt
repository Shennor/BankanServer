package ru.kfd.bankan.bankanserver.rest.payload.response

import ru.kfd.bankan.bankanserver.database.entity.BoardEntity

data class BoardInfoResponse(
    val name: String,
    val description: String,
    val creationDate: String,
    val isOpen: Boolean
)

val BoardEntity.asResponse
    get() = BoardInfoResponse(
        name!!, description!!, creationData!!.toString(), isOpen
    )