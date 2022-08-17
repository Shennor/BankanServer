package ru.kfd.bankan.bankanserver.rest.payload.response

data class JwtResponse(
    var accessToken: String,
    var id: Int,
    var login: String,
    var username: String,
    val roles: List<String>,
    val tokenType: String = "Bearer"
)