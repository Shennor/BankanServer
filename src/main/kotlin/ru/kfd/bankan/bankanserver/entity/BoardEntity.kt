package ru.kfd.bankan.bankanserver.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "board")
class BoardEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id: Int = 0,

    @Basic
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Basic
    @Column(name = "description", nullable = false)
    var description: String? = null,

    @Basic
    @Column(name = "is_open", nullable = false)
    var isOpen: Boolean = false,

    @Basic
    @Column(name = "creation_data", nullable = false)
    var creationData: Date? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as BoardEntity
        if (id != that.id) return false
        if (isOpen != that.isOpen) return false
        if (if (name != null) name != that.name else that.name != null) return false
        if (if (description != null) description != that.description else that.description != null) return false
        return !if (creationData != null) creationData != that.creationData else that.creationData != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (name != null) name.hashCode() else 0
        result = 31 * result + if (description != null) description.hashCode() else 0
        result = 31 * result + if (isOpen) 1 else 0
        result = 31 * result + if (creationData != null) creationData.hashCode() else 0
        return result
    }
}