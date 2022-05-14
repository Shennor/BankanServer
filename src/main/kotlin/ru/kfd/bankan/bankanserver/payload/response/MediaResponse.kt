package ru.kfd.bankan.bankanserver.payload.response

import ru.kfd.bankan.bankanserver.entity.BoardEntity
import ru.kfd.bankan.bankanserver.entity.CardEntity
import ru.kfd.bankan.bankanserver.entity.ListEntity
import ru.kfd.bankan.bankanserver.entity.ListToCardMappingEntity

data class ListInBoardEntity (
    var boardId: Int,
    var listEntity: ListEntity
    )

data class CardInBoardEntity (
    var boardId: Int,
    var listId: Int,
    var cardEntity: CardEntity
        )

data class BoardIdWithListToCardMappingEntities (
    var boardId: Int,
    var listToCardMappingEntities: List<ListToCardMappingEntity>
        )


data class MediaResponse (
    var boards: List<BoardEntity>,
    var lists: List<ListInBoardEntity>,
    var cards: List<CardInBoardEntity>
    )