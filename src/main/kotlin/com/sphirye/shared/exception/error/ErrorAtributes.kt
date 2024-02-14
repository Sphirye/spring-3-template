package com.sphirye.shared.exception.error

import com.sphirye.shared.exception.models.Error
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest
import java.util.*

@Component
class ErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(
        webRequest: WebRequest,
        options: ErrorAttributeOptions
    ): MutableMap<String, Any> {
        val errorAttributes = super.getErrorAttributes(webRequest, options)

        return Error(
            message = errorAttributes["error"].toString(),
            timestamp = errorAttributes["timestamp"] as Date
        ).toMap()
    }
}