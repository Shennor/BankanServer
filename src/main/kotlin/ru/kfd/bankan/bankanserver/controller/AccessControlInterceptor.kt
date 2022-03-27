package ru.kfd.bankan.bankanserver.controller

import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AccessControlInterceptor : HandlerInterceptor {
    @Throws(Exception::class)
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
            response.setHeader("Access-Control-Allow-Origin", "*")
    }
}