package ru.kfd.bankan.bankanserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Repository

@SpringBootApplication
class BankanServerApplication

fun main(args: Array<String>) {
    runApplication<BankanServerApplication>(*args)
}


// TODO Repositories whatever it is

// board repository
@Repository
class BoardRepository // TODO

// list repository
@Repository
class ListRepository // TODO

// card repository
@Repository
class CardRepository // TODO

// TODO services & controllers

/* TODO Database markup

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
