package com.sphirye.springtemplate.security.util

import com.nimbusds.jose.shaded.gson.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.lang.reflect.Type

class SimpleGrantedAuthorityUtil : JsonSerializer<Collection<SimpleGrantedAuthority>>, JsonDeserializer<Collection<SimpleGrantedAuthority>> {

    override fun serialize(src: Collection<SimpleGrantedAuthority>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val array = JsonArray()
        src.forEach { array.add(it.authority) }
        return array
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Collection<SimpleGrantedAuthority> {
        val jsonArray = json.asJsonArray
        val authorities = mutableListOf<SimpleGrantedAuthority>()
        jsonArray.forEach { element ->
            val authority = element.asJsonObject.get("authority").asString
            authorities.add(SimpleGrantedAuthority(authority))
        }
        return authorities
    }
}