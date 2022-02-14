package ru.kfd.bankan.bankanserver.entity

import javax.persistence.*

@Entity
@Table(name = "workspace")
class WorkspaceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "name")
    var name: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as WorkspaceEntity
        if (id != that.id) return false
        return !if (name != null) name != that.name else that.name != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (name != null) name.hashCode() else 0
        return result
    }
}