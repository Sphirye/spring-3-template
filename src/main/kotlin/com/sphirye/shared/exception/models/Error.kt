package com.sphirye.shared.exception.models

import java.util.*

open class Error(
    var timestamp: Date,
    var message: String,
) {

    fun toMap(): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["timestamp"] = timestamp
        map["message"] = message

        return map
    }
}