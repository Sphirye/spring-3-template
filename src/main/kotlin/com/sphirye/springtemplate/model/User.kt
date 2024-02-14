package com.sphirye.springtemplate.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "_user")
class User(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @field:NotNull
    var username: String? = null,

    @field:NotNull
    @field:Email
    var email: String? = null,

    @field:NotNull
    @field:Min(5)
    var password: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rel_user_authority",
        inverseJoinColumns = [JoinColumn(name = "authority_role", referencedColumnName = "role")],
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var authorities: MutableSet<Authority> = mutableSetOf(),
)