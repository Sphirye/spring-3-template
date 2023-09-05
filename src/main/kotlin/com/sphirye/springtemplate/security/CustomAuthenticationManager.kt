package com.sphirye.springtemplate.security

import com.sphirye.springtemplate.model.CustomUserDetails
import com.sphirye.springtemplate.model.UserIdentity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

class CustomAuthenticationManager(private val _customUserDetailsService: CustomUserDetailsService): AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {

        val userIdentity = authentication.principal as UserIdentity
        val userDetails = _customUserDetailsService.loadUserByUsername(userIdentity.email)

        val customUserDetails = CustomUserDetails(
            id = userIdentity.id,
            username = userDetails.username,
            email = userDetails.username,
            password = userDetails.password,
            authorities = userDetails.authorities
        )

        val successfulAuthentication = _createSuccessfulAuthentication(authentication, customUserDetails)
        SecurityContextHolder.getContext().authentication = successfulAuthentication
        return successfulAuthentication
    }

    private fun _createSuccessfulAuthentication(authentication: Authentication, userDetails: CustomUserDetails): Authentication {
        val token = UsernamePasswordAuthenticationToken(userDetails.username, authentication.credentials, userDetails.authorities)
        return token
    }

}