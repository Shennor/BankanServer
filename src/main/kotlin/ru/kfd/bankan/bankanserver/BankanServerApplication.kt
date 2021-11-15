package ru.kfd.bankan.bankanserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Repository
import java.util.*

@SpringBootApplication
class BankanServerApplication

fun main(args: Array<String>) {
    runApplication<BankanServerApplication>(*args)
}

// Notes about business-logic

//  info collects all visible attributes

// board class

data class BoardInfo(
    val id: UInt,
    val name: String,
    val description: String,
    val creationDate: Date,
    val usersIds: List<UInt>
)

// Board is returned by request ReadBoard
data class Board(
    val lists: List<BListInfo>,
    val listsOrder: List<UInt>
)

// list class

data class BListInfo(
    val id: UInt,
    val name: String,
    val creationDate: Date
)

// BList is returned by request ReadList
data class BList(
    val cards: List<CardInfo>,
    val cardsIdsOrder: List<UInt>
)

// card class

data class CardInfo(
    val id: UInt,
    val name: String,
    val color: UInt,
    val creationDate: Date,
    val deadlineDate: Date,
    val creatorId: UInt,
    val executorsIds: List<UInt>
)

sealed class Content {
    data class Text(val text: String) : Content()
    abstract class CheckList() : Content()
    abstract class Image() : Content()
    //...
}

data class Card(
    val contentList: List<Content>
)

// board repository

@Repository
class BoardRepository

// list repository

@Repository
class ListRepository 

// card repository

@Repository
class CardRepository 

// controllers


/*
   CardTable
   CardId | Name | Color | CreationDate | DeadlineDate | CreatorId | -assignedUserIds- | CardContent

   CardToAssignedUsers
   Key | UserId | CardId

   ListToCardMapping
   Key | ListId | CardId | IndexOfCardInList

   ListTable
   ListId | Name | CreationDate

   BoardToListMapping
   Key | BoardId | ListId | IndexOfListInBoard

   BoardTable
   BoardId | Name | Description | CreationDate

   BoardToAssignedUsers
   Key | UserId | BoardIs

   WorkspaceToTable
   Key | Workspace | Board | IndexOfBoardInWorkspace

   UserInfoTable
   UserId | UserAttributes (login, name)... |

   PasswordsHash-Table
   UserId | Password

   Settings
   UserId | Settings (JSON)
 */


