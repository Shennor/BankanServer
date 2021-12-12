package ru.kfd.bankan.bankanserver.payload.request


data class LoginRequest(
    var login: String,
    var password: String,
)