package ru.kfd.bankan.bankanserver.entity

import javax.persistence.*

@Entity
@Table(name = "user_to_workspace_mapping")
class UserToWorkspaceMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "user_id")
    var userId = 0

    @Basic
    @Column(name = "workspace_id")
    var workspaceId = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserToWorkspaceMappingEntity
        if (id != that.id) return false
        if (userId != that.userId) return false
        return workspaceId == that.workspaceId
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + workspaceId
        return result
    }
}