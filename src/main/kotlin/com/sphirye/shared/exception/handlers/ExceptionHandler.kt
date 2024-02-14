package com.sphirye.shared.exception.handlers

import com.sphirye.shared.exception.exceptions.CredentialsExpiredException
import com.sphirye.shared.exception.http.CustomHttpStatus
import com.sphirye.shared.exception.models.Error
import com.sphirye.shared.exception.models.ValidationError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.util.*

@ControllerAdvice(annotations = [RestController::class])
class ExceptionHandler {

    @ExceptionHandler(CredentialsExpiredException::class)
    fun expiredTokenException(e: Exception): ResponseEntity<*>? {
        return ResponseEntity
            .status(CustomHttpStatus.CREDENTIALS_EXPIRED.value())
            .body<Any>(
                Error(
                    message = e.message!!,
                    timestamp = Date(System.currentTimeMillis()),
                )
            )
    }

    @ExceptionHandler(BindException::class)
    fun bindException(e: BindException): ResponseEntity<*>? {
        val message = "Validation errors"
        val errors = mutableMapOf<String, MutableList<String>>()

        e.bindingResult.fieldErrors.forEach {
            if (!errors.keys.contains(it.field)) {
                errors[it.field] = mutableListOf()
            }

            errors[it.field]!!.add(it.defaultMessage!!)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<Any>(
            ValidationError(Date(), message, errors)
        )
    }

}