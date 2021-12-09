package ru.kfd.bankan.bankanserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {
    @GetMapping("/")
    fun homePage() : String
    {
        return "home"
    }

    @GetMapping("/auth")
    fun pageForAuthenticatedUsers() : String {
        return "secured part of web service"
    }

}