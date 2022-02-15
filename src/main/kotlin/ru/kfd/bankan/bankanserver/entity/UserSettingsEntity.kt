package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.UserSettingsEntity
import javax.persistence.*

@Entity
@Table(name = "user_settings")
class UserSettingsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Column(name = "user_id")
    var userId = 0

    @Column(name = "settings")
    var settings: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserSettingsEntity
        if (id != that.id) return false
        if (userId != that.userId) return false
        return if (if (settings != null) settings != that.settings else that.settings != null) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + if (settings != null) settings.hashCode() else 0
        return result
    }
}