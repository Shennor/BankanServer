package ru.kfd.bankan.bankanserver.entity

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "user_info", schema = "bankan")
class UserInfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    var id = 0

    @Basic
    @Column(name = "name")
    var name: String? = null

    @Basic
    @Column(name = "registration_date")
    var registrationDate: Date? = null


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserInfoEntity
        if (id != that.id) return false
        if (if (name != null) name != that.name else that.name != null) return false
        return if (if (registrationDate != null) registrationDate != that.registrationDate else that.registrationDate != null) false else true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (name != null) name.hashCode() else 0
        result =
            31 * result + if (registrationDate != null) registrationDate.hashCode() else 0
        return result
    }
}