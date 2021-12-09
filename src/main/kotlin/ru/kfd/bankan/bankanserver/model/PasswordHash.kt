package ru.kfd.bankan.bankanserver.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "password_hash")
data class PasswordHash(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserInfo,

    @Column(name = "hash", nullable = false, length = 16)
    val hash: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(
                other
            )
        ) return false
        other as PasswordHash

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , user = $user , hash = $hash )"
    }
}
