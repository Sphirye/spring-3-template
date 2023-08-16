package com.sphirye.springtemplate.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails (
    private val id: Long?,
    private val username: String?,
    private val email: String?,
    private val password: String?,
    private val authorities: Collection<SimpleGrantedAuthority>?
) : UserDetails {
    private val accountExpiredYn = false
    private val accountLockedYn = false
    private val credentialsExpiredYn = false
    private val enabledYn = true

    override fun getUsername() = username

    override fun getPassword() = password

    override fun getAuthorities() = authorities

    override fun isAccountNonExpired() = !accountExpiredYn

    override fun isAccountNonLocked() = !accountLockedYn

    override fun isCredentialsNonExpired() = !credentialsExpiredYn

    override fun isEnabled() = enabledYn
}