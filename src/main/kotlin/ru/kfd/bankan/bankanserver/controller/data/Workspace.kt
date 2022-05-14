package ru.kfd.bankan.bankanserver.controller.data

import org.hibernate.annotations.Parameter
import org.springframework.data.jpa.repository.Query
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.controller.AllowedTo
import ru.kfd.bankan.bankanserver.controller.safeFindById
import ru.kfd.bankan.bankanserver.payload.response.*
import ru.kfd.bankan.bankanserver.repository.*

// TODO
@CrossOrigin(origins = ["http://localhost:8080"])
@RequestMapping("/api/workspace")
@RestController
class Workspace(
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceToBoardMappingRepository: WorkspaceToBoardMappingRepository,
    private val boardToListMappingRepository: BoardToListMappingRepository,
    private val listToCardMappingRepository: ListToCardMappingRepository,
    private val boardRepository: BoardRepository,
    private val listRepository: ListRepository,
    private val cardRepository: CardRepository,
    private val userToWorkspaceMappingRepository: UserToWorkspaceMappingRepository,
    private val allowedTo: AllowedTo,
) {
    @GetMapping("/{workspaceId}")
    fun getWorkspace(@PathVariable workspaceId: Int): WorkspaceResponse {
        // check if workspaceId exists
        val workspaceEntity = workspaceRepository.safeFindById(workspaceId)
        // check if user have permission to read workspace (it's their)
        allowedTo.readByWorkspaceId(workspaceId)
        // read
        val mappingList = workspaceToBoardMappingRepository.findAllByWorkspaceId(workspaceId)
        return WorkspaceResponse(
            workspaceEntity.id,
            workspaceEntity.name!!,
            (mappingList.map { boardRepository.safeFindById(it.boardId) }).toList()
        )
    }

    @GetMapping("/user/{userId}")
    fun getWorkspaceByUserId(@PathVariable userId: Int): WorkspaceResponse {
        // check if person exists
        val mappingEntity = userToWorkspaceMappingRepository.safeFindById(userId);
        return getWorkspace(mappingEntity.workspaceId!!)
    }

    @GetMapping("/media/{workspaceId}")
    fun getAllMedia(@PathVariable workspaceId: Int): MediaResponse {
        // check if workspaceId exists
        val workspaceEntity = workspaceRepository.safeFindById(workspaceId)
        // check if user have permission to read workspace (it's their)
        allowedTo.readByWorkspaceId(workspaceId)
        // get all media
        val mappingBoards = workspaceToBoardMappingRepository.findAllByWorkspaceId(workspaceId)
        // 1
        val listOfBoardEntities = (mappingBoards.map { boardRepository.safeFindById(it.boardId) }).toList()
        val mappingLists =
            (listOfBoardEntities.flatMap { boardToListMappingRepository.findAllByBoardId(it.id) }).toList()
//        return MediaResponse(listOfBoardEntities, emptyList(), emptyList())
        // 2
        val listOfListInBoardEntities = (mappingLists.map {
            ListInBoardEntity(boardId = it!!.boardId, listEntity = listRepository.safeFindById(it.listId))
        })
        val boardIdWithListToCardMappingEntities = (mappingLists.map {
            BoardIdWithListToCardMappingEntities(
                boardId = it!!.boardId,
                listToCardMappingEntities = listToCardMappingRepository.findAllByListId(it!!.listId)
            )
        })
            .toList()
        // 3
        val arrayOfCardInBoardEntities =
            boardIdWithListToCardMappingEntities.flatMap { e: BoardIdWithListToCardMappingEntities ->
                e.listToCardMappingEntities.map {
                    CardInBoardEntity(
                        boardId = e.boardId,
                        listId = it.listId,
                        cardEntity = cardRepository.safeFindById(it.cardId)
                    )
                }
            }

        return MediaResponse(listOfBoardEntities, listOfListInBoardEntities, arrayOfCardInBoardEntities.toList())
    }
}
