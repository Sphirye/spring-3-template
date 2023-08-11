package com.sphirye.springtemplate.security.util

import com.nimbusds.jose.shaded.gson.Gson
import com.sphirye.springtemplate.model.CustomUserTokenDetails
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenUtil {

    @Value("\${app.jwt.secret-key}")
    private lateinit var _jwtSecret: String

    @Value("\${spring.application.id}")
    private lateinit var _jwtIssuer: String

    private val logger: Logger? = LogManager.getLogger()
    private val _signingKey: Key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(_jwtSecret)) }

    fun generateAccessToken(customUserTokenDetails: CustomUserTokenDetails): String {
        val oneWeekExpirationTime = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)

        return Jwts.builder()
            .setSubject(customUserTokenDetails.id.toString())
            .setIssuer(_jwtIssuer)
            .setIssuedAt(Date())
            .setExpiration(oneWeekExpirationTime)
            .signWith(_signingKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun resolveSubject(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(_signingKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun getExpirationDate(token: String): Date {
        val claims = Jwts.parserBuilder()
            .setSigningKey(_signingKey)
            .build()
            .parseClaimsJws(token)
            .body

        return claims.expiration
    }

    fun validate(token: String) {
        Jwts.parserBuilder()
            .setSigningKey(_signingKey)
            .build()
            .parseClaimsJws(token)
    }

    fun resolveUserDetailsFromToken(token: String): CustomUserTokenDetails? {
        val claim = Jwts.parserBuilder()
            .setSigningKey(_signingKey)
            .build()
            .parseClaimsJws(token)
            .body["details"]

        return if (claim != null) { Gson().fromJson(claim.toString(), CustomUserTokenDetails::class.java) }
        else { null }
    }

}