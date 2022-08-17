package ru.kfd.bankan.bankanserver.rest.payload.request


data class LoginRequest(
    var login: String,
    var password: String,
)