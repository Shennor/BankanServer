package ru.kfd.bankan.bankanserver.entity

import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity
import javax.persistence.*

@Entity
@Table(name = "auth_info", schema = "bankan", catalog = "")
class AuthInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "login")
    var login: String? = null

    @Basic
    @Column(name = "password_hash")
    var passwordHash: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as AuthInfoEntity
        if (id != that.id) return false
        if (if (login != null) login != that.login else that.login != null) return false
        return if (if (passwordHash != null) passwordHash != that.passwordHash else that.passwordHash != null) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (login != null) login.hashCode() else 0
        result =
            31 * result + if (passwordHash != null) passwordHash.hashCode() else 0
        return result
    }
}