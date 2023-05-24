package com.duv.internproject.controller.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun IllegalArgumentException(request: HttpServletRequest, exception: Exception): ResponseEntity<ResponseError>{
        val errorResponse =ResponseError(statusCode = HttpStatus.BAD_REQUEST.value(), exception.message!!)
        return ResponseEntity.badRequest().body(errorResponse)
    }


}

data class ResponseError (val statusCode: Int, val message: String)
