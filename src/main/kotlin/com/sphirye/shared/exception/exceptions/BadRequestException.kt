package com.sphirye.shared.exception.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException : ApplicationException {
    constructor(message: String?): super(message)
    constructor(): super("Bad Request")
}