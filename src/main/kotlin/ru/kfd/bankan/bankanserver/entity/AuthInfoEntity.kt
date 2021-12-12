package ru.kfd.bankan.bankanserver.entity

import javax.persistence.*

@Entity
@Table(name = "auth_info", schema = "bankan")
class AuthInfoEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    var id: Int = 0,

    @Basic
    @Column(name = "user_id", nullable = false)
    var userId: Int = 0,

    @Basic
    @Column(name = "login", nullable = false)
    var login: String,

    @Basic
    @Column(name = "password_hash", nullable = false)
    var passwordHash: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as AuthInfoEntity
        if (id != that.id) return false
        if (userId != that.userId) return false
        if (login != that.login) return false
        return passwordHash == that.passwordHash
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId
        result = 31 * result + login.hashCode()
        result =
            31 * result + passwordHash.hashCode()
        return result
    }
}