package ru.kfd.bankan.bankanserver.rest.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


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