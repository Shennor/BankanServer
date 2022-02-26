package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.BoardEntity
import java.util.*

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