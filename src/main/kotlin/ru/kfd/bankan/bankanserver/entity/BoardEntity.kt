package ru.kfd.bankan.bankanserver.entity

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "board")
class BoardEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "name")
    var name: String? = null

    @Basic
    @Column(name = "description")
    var description: String? = null

    @Basic
    @Column(name = "is_open")
    var isOpen: Byte = 0

    @Basic
    @Column(name = "creation_data")
    var creationData: Date? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BoardEntity
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
        result = 31 * result + isOpen.toInt()
        result = 31 * result + if (creationData != null) creationData.hashCode() else 0
        return result
    }
}