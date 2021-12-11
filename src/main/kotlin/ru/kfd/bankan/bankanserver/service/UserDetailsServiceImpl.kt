package ru.kfd.bankan.bankanserver.service


import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.kfd.bankan.bankanserver.repository.AuthInfoRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ru.kfd.bankan.bankanserver.repository.PasswordRepository

@Primary
@Service
class UserDetailsServiceImpl(
    val userInfoRepository: AuthInfoRepository,
    val passwordRepository: PasswordRepository
) : UserDetailsService {

    override fun loadUserByUsername(login: String): UserDetails? {
        val user = userInfoRepository.findByLogin(login) ?: throw UsernameNotFoundException("User $login not found")
        val passwordHash = passwordRepository.findByUserId(user.id).hash ?: throw InternalError("Password for user $login not found in database")
        return UserDetailsImpl(
            username = user.login,
            password = passwordHash,
            authorities = listOf(SimpleGrantedAuthority("USER")))
    }
}

