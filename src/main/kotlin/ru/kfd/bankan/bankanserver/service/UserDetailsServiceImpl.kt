package ru.kfd.bankan.bankanserver.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.kfd.bankan.bankanserver.repository.AuthInfoRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity
import ru.kfd.bankan.bankanserver.entity.UserInfoEntity
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository

@Primary
@Service
class UserDetailsServiceImpl(
    val authInfoRepository: AuthInfoRepository,
    val userInfoRepository: UserInfoRepository

) : UserDetailsService {

    override fun loadUserByUsername(login: String): UserDetails? {
        val user = authInfoRepository.findByLogin(login) ?: throw UsernameNotFoundException("User $login not found")
        return UserDetailsImpl(
            username = user.login!!,
            password = user.passwordHash!!,
            authorities = listOf(SimpleGrantedAuthority("USER")))
    }

    fun saveUser(auth : AuthInfoEntity, user : UserInfoEntity) : Boolean{
        if (authInfoRepository.existsByLogin(auth.login!!)){
            return false;
        }
        val newUserId = userInfoRepository.save(user).id
        auth.userId = newUserId
        authInfoRepository.save(auth)
        return true
    }
}

