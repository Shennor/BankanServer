package ru.kfd.bankan.bankanserver.controller.data

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.kfd.bankan.bankanserver.controller.IdNotFoundException

@ControllerAdvice
class DataApiExceptionHandler {
    @ExceptionHandler(value = [IdNotFoundException::class])
    fun idNotFound(exception: IdNotFoundException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.message, HttpStatus.NOT_FOUND)
    }
}