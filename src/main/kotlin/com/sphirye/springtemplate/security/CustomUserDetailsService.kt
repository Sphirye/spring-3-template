package com.sphirye.springtemplate.security

import com.sphirye.springtemplate.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("userDetailsService")
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var _userService: UserService

    @Transactional
    override fun loadUserByUsername(email: String): UserDetails {
        val user = _userService.findByEmail(email)
        return _createUserDetails(user)

    }

    private fun _createUserDetails(user: com.sphirye.springtemplate.model.User): User {
        return User(user.email, user.password, listOf())
    }
}