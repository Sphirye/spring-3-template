package com.sphirye.springtemplate.security

import com.sphirye.springtemplate.model.UserIdentity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SessionManager {

    fun getUserEmail(): String {
        val identity = SecurityContextHolder.getContext().authentication.principal as UserIdentity
        return identity.email
    }

}