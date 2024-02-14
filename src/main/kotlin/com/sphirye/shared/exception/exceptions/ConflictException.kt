package com.sphirye.shared.exception.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ConflictException : ApplicationException {
    constructor(message: String?): super(message)
    constructor(): super("Conflict Exception")
}