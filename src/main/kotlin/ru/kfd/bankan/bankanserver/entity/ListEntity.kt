package ru.kfd.bankan.bankanserver.entity

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "list")
class ListEntity(
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @Column(name = "name", nullable = false)
    @Basic
    var name: String? = null,

    @Column(name = "description", nullable = false)
    @Basic
    var description: String? = null,

    @Column(name = "creation_data", nullable = false)
    @Basic
    var creationData: Date? = null
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ListEntity
        if (if (id != null) id != that.id else that.id != null) return false
        if (if (name != null) name != that.name else that.name != null) return false
        if (if (description != null) description != that.description else that.description != null) return false
        return !if (creationData != null) creationData != that.creationData else that.creationData != null
    }

    override fun hashCode(): Int {
        var result = if (id != null) id.hashCode() else 0
        result = 31 * result + if (name != null) name.hashCode() else 0
        result = 31 * result + if (description != null) description.hashCode() else 0
        result = 31 * result + if (creationData != null) creationData.hashCode() else 0
        return result
    }
}