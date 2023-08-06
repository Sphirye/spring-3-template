package com.sphirye.springtemplate.config

import com.sphirye.springtemplate.service.UserService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PopulateConfig {

    @Autowired
    private lateinit var userService: UserService

    @PostConstruct
    fun init() {
        userService.init()
    }
}