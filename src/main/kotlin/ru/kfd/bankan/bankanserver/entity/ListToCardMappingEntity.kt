package ru.kfd.bankan.bankanserver.entity

import javax.persistence.*

@Entity
@Table(name = "list_to_card_mapping")
class ListToCardMappingEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id: Int = 0,

    @Basic
    @Column(name = "list_id", nullable = false)
    var listId: Int,

    @Basic
    @Column(name = "card_id", nullable = false)
    var cardId: Int,

    @Basic
    @Column(name = "index_of_card_in_list", nullable = false)
    var indexOfCardInList: Int,
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ListToCardMappingEntity
        if (id != that.id) return false
        if (listId != that.listId) return false
        if (cardId != that.cardId) return false
        return indexOfCardInList == that.indexOfCardInList
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + listId
        result = 31 * result + cardId
        result = 31 * result + indexOfCardInList
        return result
    }
}