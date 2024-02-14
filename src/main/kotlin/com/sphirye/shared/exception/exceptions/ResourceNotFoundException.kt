package com.sphirye.shared.exception.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException : ApplicationException {
    constructor(message: String?): super(message)
    constructor(): super("Resource not found")
}