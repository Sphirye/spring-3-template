package com.sphirye.springtemplate.model

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
@Table(name = "_user")
class User(
    @Id
    @GeneratedValue
    var id: Long? = null,

    var username: String? = null,

    var email: String? = null,

    var password: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rel_user_authority",
        inverseJoinColumns = [JoinColumn(name = "authority_role", referencedColumnName = "role")],
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var authorities: MutableSet<Authority> = mutableSetOf(),
) {

    fun getSimpleGrantedAuthorities(): List<SimpleGrantedAuthority> {
        return authorities.map { it.role.toString() }.map { SimpleGrantedAuthority(it) }
    }

}