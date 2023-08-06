package com.sphirye.springtemplate.controller

import com.sphirye.springtemplate.model.JwtToken
import com.sphirye.springtemplate.model.UserCredentials
import com.sphirye.springtemplate.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController {

    @Autowired lateinit var authService: AuthService

    @PostMapping("/core/auth/login")
    fun postLogin(@RequestBody credentials: UserCredentials): JwtToken {
        return authService.login(credentials)
    }
}