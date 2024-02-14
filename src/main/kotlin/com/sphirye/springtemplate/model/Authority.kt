package com.sphirye.springtemplate.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sphirye.shared.utils.Identifiable
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
class Authority (

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    override var id: Role?,

    var description: String? = null,

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    var users: MutableSet<User> = mutableSetOf(),

    @Column(nullable = false)
    var enabled: Boolean? = true,

    ): Identifiable<Authority.Role> {
    enum class Role {
        SUPER_ADMIN, ADMIN, MOD
    }

    companion object {
        fun getSimpleGrantedAuthoritiesFrom(authorities: MutableSet<Authority>): List<SimpleGrantedAuthority> {
            return authorities.map { it.id.toString() }.map { SimpleGrantedAuthority(it) }
        }
    }
}