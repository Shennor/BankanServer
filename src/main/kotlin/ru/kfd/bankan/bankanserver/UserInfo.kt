package ru.kfd.bankan.bankanserver

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*

@Table(
    name = "user_info", indexes = [
        Index(name = "login", columnList = "login", unique = true)
    ]
)
@Entity
data class UserInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int,

    @Column(name = "login", nullable = false, length = 20)
    val login: String,

    @Column(name = "name", nullable = false, length = 40)
    val name: String,

    @Column(name = "registration_date", nullable = false)
    val registrationDate: LocalDate = LocalDate.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(
                other
            )
        ) return false
        other as UserInfo

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , login = $login , name = $name , registrationDate = $registrationDate )"
    }
}
