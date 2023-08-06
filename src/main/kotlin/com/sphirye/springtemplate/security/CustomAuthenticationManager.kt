package com.sphirye.springtemplate.security

import com.sphirye.springtemplate.model.UserIdentity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomAuthenticationManager(private val customUserDetailsService: CustomUserDetailsService): AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {
        val userIdentity = authentication.principal as UserIdentity

        return try {
            val userDetails = customUserDetailsService.loadUserByUsername(userIdentity.email)
            createSuccessfulAuthentication(authentication, userDetails, userIdentity)
        } catch (exception: UsernameNotFoundException) {
            throw BadCredentialsException("invalid login details")
        }
    }

    private fun createSuccessfulAuthentication(
        authentication: Authentication,
        user: UserDetails,
        userIdentityModel: UserIdentity
    ): Authentication {
        val token = UsernamePasswordAuthenticationToken(userIdentityModel, authentication.credentials, user.authorities)
        token.details = authentication.details
        return token
    }

}