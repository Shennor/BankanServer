package ru.kfd.bankan.bankanserver.service


import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity
import ru.kfd.bankan.bankanserver.entity.UserInfoEntity
import ru.kfd.bankan.bankanserver.repository.AuthInfoRepository
import ru.kfd.bankan.bankanserver.repository.UserInfoRepository

@Primary
@Service
class UserDetailsServiceImpl(
    val authInfoRepository: AuthInfoRepository,
    val userInfoRepository: UserInfoRepository
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(login: String): UserDetails {
        if (!authInfoRepository.existsByEmail(login))
            throw UsernameNotFoundException("User $login not found")

        val user = authInfoRepository.findByEmail(login)!!

        return UserDetailsImpl.build(user)
    }

    fun saveUser(auth: AuthInfoEntity, user: UserInfoEntity): Boolean {
        if (authInfoRepository.existsByEmail(auth.email)) {
            return false
        }
        val newUserId = userInfoRepository.save(user).id
        auth.userId = newUserId
        authInfoRepository.save(auth)
        return true
    }
}

