package ru.kfd.bankan.bankanserver.rest.payload.response

import ru.kfd.bankan.bankanserver.database.entity.BoardEntity

data class WorkspaceResponse(
    val id: Int,
    val name: String,
    val listOfBoardEntities: List<BoardEntity>
)