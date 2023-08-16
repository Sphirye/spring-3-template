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
    private lateinit var _userService: UserService

    @Autowired
    private lateinit var _authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var _passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var _jwtTokenUtil: JwtTokenUtil

    fun login(credentials: UserCredentials): JwtToken {
        val user = _userService.findByEmail(credentials.email)

        if (!_passwordEncoder.matches(credentials.password, user.password)) {
            throw BadCredentialsException("Bad credentials")
        }

        val userIdentity = UserIdentity(email = user.email!!, user.id!!)

        val auth = _authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userIdentity, user.password, listOf())
        )

        return JwtToken(token = _jwtTokenUtil.generateAccessToken(userIdentity.id))
    }
}