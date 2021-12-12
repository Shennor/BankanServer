package ru.kfd.bankan.bankanserver.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView
import ru.kfd.bankan.bankanserver.dto.UserDto
import ru.kfd.bankan.bankanserver.service.UserDetailsServiceImpl
import javax.servlet.http.HttpServletRequest


@Controller
class RegistrationController(
    userDetailsServiceImpl: UserDetailsServiceImpl
) {

    @GetMapping("/user/registration")
    fun showRegistrationForm(request: WebRequest?, model: Model): String? {
        val userDto = UserDto()
        model.addAttribute("user", userDto)
        return "registration"
    }

//    fun registerUserAccount(
//        @ModelAttribute("user") @Valid userDto: UserDto?,
//        request: HttpServletRequest?, errors: Errors?
//    ): ModelAndView? {
//        //...
//    }

}
