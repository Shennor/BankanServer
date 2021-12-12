package ru.kfd.bankan.bankanserver.service


import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.kfd.bankan.bankanserver.repository.AuthInfoRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.transaction.annotation.Transactional

@Primary
@Service
class UserDetailsServiceImpl(
    val authInfoRepository: AuthInfoRepository,
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(login: String): UserDetails {
        if (authInfoRepository.existsByLogin(login))
            throw UsernameNotFoundException("User $login not found")

        val user = authInfoRepository.findByLogin(login)

        return UserDetailsImpl(
            userId = user.userId,
            username = user.login,
            password = user.passwordHash,
            authorities = listOf(SimpleGrantedAuthority("USER"))
        )
    }
}

