package com.sphirye.springtemplate.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SessionManager {

    fun getUserEmail(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    fun getUserAuthorities(): Any? {
        return SecurityContextHolder.getContext().authentication.authorities
    }

}