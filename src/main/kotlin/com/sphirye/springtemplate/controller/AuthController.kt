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

    @Autowired
    private lateinit var _authService: AuthService

    @PostMapping("/auth/login")
    fun postLogin(@RequestBody credentials: UserCredentials): JwtToken {
        return _authService.login(credentials)
    }

}