package ru.kfd.bankan.bankanserver.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.JwtUtils
import ru.kfd.bankan.bankanserver.payload.request.LoginRequest
import ru.kfd.bankan.bankanserver.payload.request.SignupRequest
import ru.kfd.bankan.bankanserver.payload.response.JwtResponse
import ru.kfd.bankan.bankanserver.payload.response.MessageResponse
import ru.kfd.bankan.bankanserver.repository.AuthInfoRepository
import ru.kfd.bankan.bankanserver.service.UserDetailsImpl
import java.security.Principal
import java.util.stream.Collectors
import javax.validation.Valid



@RestController
class MainController {
    @GetMapping("/")
    fun homePage(): String {
        return "home"
    }

    @GetMapping("/auth")
    fun pageForAuthenticatedUsers(principal: Principal): String {
        return "secured part of web service : " + principal.name
    }
}