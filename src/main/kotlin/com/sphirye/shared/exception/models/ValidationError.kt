package com.sphirye.shared.exception.models

import java.util.*

class ValidationError(
    timestamp: Date,
    message: String,
    var errors: MutableMap<String, MutableList<String>>
): Error(timestamp, message)