package com.sphirye.shared.exception.exceptions

class CredentialsExpiredException: ApplicationException {
    constructor(message: String?): super(message)
    constructor(): super("Unauthorized Exception")
}