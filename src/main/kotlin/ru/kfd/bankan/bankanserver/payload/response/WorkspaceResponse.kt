package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.BoardEntity

data class WorkspaceResponse(
    val id: Int,
    val name: String,
    val listOfBoardEntities: List<BoardEntity>
)