package ru.kfd.bankan.bankanserver.service

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity
import java.util.*


class UserDetailsImpl(
    private val userId: Int,
    private val username: String,
    @JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    companion object {
        fun build(user: AuthInfoEntity): UserDetailsImpl {
            return UserDetailsImpl(
                user.userId,
                user.email,
                user.passwordHash,
                listOf(SimpleGrantedAuthority("USER"))
            )
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    fun getId(): Int = userId
    fun getEmail(): String = username
    override fun getPassword(): String = password
    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val user = o as UserDetailsImpl
        return Objects.equals(userId, user.userId)
    }
}

