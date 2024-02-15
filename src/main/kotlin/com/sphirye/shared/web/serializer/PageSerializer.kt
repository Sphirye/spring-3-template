package com.sphirye.shared.web.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.data.domain.Page

@JsonComponent
class PageSerializer {

    class Serialize : JsonSerializer<Page<*>>() {
        override fun serialize(value: Page<*>?, jsonGenerator: JsonGenerator, provider: SerializerProvider?) {
            if (value == null) {
                jsonGenerator.writeNull()
                return
            }

            jsonGenerator.writeObject(value.content)
        }
    }
}