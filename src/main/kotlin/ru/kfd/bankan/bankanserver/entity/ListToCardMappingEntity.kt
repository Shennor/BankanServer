package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.ListToCardMappingEntity
import javax.persistence.*

@Entity
@Table(name = "list_to_card_mapping")
class ListToCardMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "list_id")
    var listId = 0

    @Basic
    @Column(name = "card_id")
    var cardId = 0

    @Basic
    @Column(name = "index_of_card_in_list")
    var indexOfCardInList = 0
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ListToCardMappingEntity
        if (id != that.id) return false
        if (listId != that.listId) return false
        if (cardId != that.cardId) return false
        return if (indexOfCardInList != that.indexOfCardInList) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + listId
        result = 31 * result + cardId
        result = 31 * result + indexOfCardInList
        return result
    }
}