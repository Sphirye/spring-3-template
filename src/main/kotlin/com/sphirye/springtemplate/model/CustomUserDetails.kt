package com.sphirye.springtemplate.model

import org.springframework.security.core.GrantedAuthority
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

    override fun getUsername(): String? { return username }
    override fun getPassword(): String? { return password }
    override fun getAuthorities(): Collection<GrantedAuthority?>? { return authorities }
    override fun isAccountNonExpired(): Boolean { return !accountExpiredYn }
    override fun isAccountNonLocked(): Boolean { return !accountLockedYn }
    override fun isCredentialsNonExpired(): Boolean { return !credentialsExpiredYn }
    override fun isEnabled(): Boolean { return enabledYn }
    fun toCustomUserTokenDetails(): CustomUserTokenDetails { return CustomUserTokenDetails(id, email, authorities) }
}