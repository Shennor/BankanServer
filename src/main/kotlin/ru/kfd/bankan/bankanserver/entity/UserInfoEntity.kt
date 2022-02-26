package ru.kfd.bankan.bankanserver.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "user_info", schema = "bankan")
class UserInfoEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    var id: Int = 0,

    @Basic
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "registration_date", nullable = false)
    val registrationDate: LocalDate? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as UserInfoEntity
        if (id != that.id) return false
        if (name != that.name) return false
        return registrationDate == that.registrationDate
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result =
            31 * result + registrationDate.hashCode()
        return result
    }
}

