package ru.kfd.bankan.bankanserver.payload.response

data class JwtResponse(
    var accessToken: String,
    var id: Int,
    var login: String,
    val roles: List<String>,
    val tokenType: String = "Bearer"
)