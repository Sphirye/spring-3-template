package com.sphirye.springtemplate.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Authority (

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role,

    var description: String? = null,

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "rel_user_authority",
        joinColumns = [JoinColumn(name = "authority_role", referencedColumnName = "role")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var users: MutableSet<User> = mutableSetOf(),

    @Column(nullable = false)
    var enabled: Boolean,

    ) {
    enum class Role {
        SUPER_ADMIN, ADMIN, MOD
    }
}