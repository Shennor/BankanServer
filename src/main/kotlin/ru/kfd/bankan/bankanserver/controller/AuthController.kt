package ru.kfd.bankan.bankanserver.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import ru.kfd.bankan.bankanserver.JwtUtils
import ru.kfd.bankan.bankanserver.entity.AuthInfoEntity
import ru.kfd.bankan.bankanserver.entity.UserInfoEntity
import ru.kfd.bankan.bankanserver.entity.UserToWorkspaceMappingEntity
import ru.kfd.bankan.bankanserver.entity.WorkspaceEntity
import ru.kfd.bankan.bankanserver.payload.request.LoginRequest
import ru.kfd.bankan.bankanserver.payload.request.SignupRequest
import ru.kfd.bankan.bankanserver.payload.response.JwtResponse
import ru.kfd.bankan.bankanserver.payload.response.MessageResponse
import ru.kfd.bankan.bankanserver.repository.*
import ru.kfd.bankan.bankanserver.service.UserDetailsImpl
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userInfoRepository: UserInfoRepository,
    private val authInfoRepository: AuthInfoRepository,
    private val userToWorkspaceMappingRepository: UserToWorkspaceMappingRepository,
    private val workspaceRepository: WorkspaceRepository,
    private val encoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.login,
                loginRequest.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.username,
            roles
        )
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest): ResponseEntity<*> {
        if (authInfoRepository.existsByEmail(signUpRequest.login)) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Login is already taken!"))
        }
        val user = UserInfoEntity(
            name = signUpRequest.username
        )
        val newUser = userInfoRepository.save(user)
        val auth = AuthInfoEntity(
            userId = newUser.id,
            email = signUpRequest.login,
            passwordHash = encoder.encode(signUpRequest.password)
        )
        val workspace = WorkspaceEntity()
        workspace.name = newUser.name
        authInfoRepository.save(auth)
        val newWorkspace = workspaceRepository.save(workspace)
        val mapping = UserToWorkspaceMappingEntity()
        mapping.userId = newUser.id
        mapping.workspaceId = newWorkspace.id
        userToWorkspaceMappingRepository.save(mapping)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }
}