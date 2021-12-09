package ru.kfd.bankan.bankanserver.service


import net.bytebuddy.asm.Advice
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository
import org.springframework.security.core.userdetails.UserDetailsService
import javax.transaction.Transactional

@Primary
@Service
class UserDetailsServiceImpl(
    val userInfoRepository: UserInfoRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        TODO()
    }
}

