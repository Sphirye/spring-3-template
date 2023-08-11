package com.sphirye.springtemplate.model

import com.nimbusds.jose.shaded.gson.annotations.JsonAdapter
import com.sphirye.springtemplate.security.util.SimpleGrantedAuthorityUtil
import org.springframework.security.core.authority.SimpleGrantedAuthority

class CustomUserTokenDetails(
    val id: Long?,
    val email: String?,
    @JsonAdapter(SimpleGrantedAuthorityUtil::class)
    val authorities: Collection<SimpleGrantedAuthority>?
)