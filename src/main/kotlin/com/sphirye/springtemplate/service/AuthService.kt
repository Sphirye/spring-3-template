package com.sphirye.springtemplate.service

import com.sphirye.springtemplate.model.JwtToken
import com.sphirye.springtemplate.model.UserCredentials
import com.sphirye.springtemplate.model.UserIdentity
import com.sphirye.springtemplate.security.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    fun login(credentials: UserCredentials): JwtToken {
        val user = userService.findByEmail(credentials.email)

        if (!passwordEncoder.matches(credentials.password, user.password)) {
            throw BadCredentialsException("Bad credentials")
        }

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                UserIdentity(email = user.email!!, user.id!!), user.password, listOf()
            )
        )

        return JwtToken(token = jwtTokenUtil.generateAccessToken(user.id.toString()))
    }
}