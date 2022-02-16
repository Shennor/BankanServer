package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.kfd.bankan.bankanserver.controller.IdNotFoundException
import ru.kfd.bankan.bankanserver.controller.ResourceNotAllowedToUser
import ru.kfd.bankan.bankanserver.controller.UserNotFoundException

@ControllerAdvice
class DataApiExceptionHandler {
    @ExceptionHandler(value = [IdNotFoundException::class])
    fun idNotFound(exception: IdNotFoundException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [UserNotFoundException::class])
    fun userNotFound(exception: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [ResourceNotAllowedToUser::class])
    fun resourceNotAllowed(exception: ResourceNotAllowedToUser): ResponseEntity<String> {
        return ResponseEntity<String>(exception.message, HttpStatus.FORBIDDEN)
    }
}