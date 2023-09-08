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
    @ManyToMany(mappedBy = "authorities")
    var users: MutableSet<User> = mutableSetOf(),

    @Column(nullable = false)
    var enabled: Boolean? = true,

    ) {
    enum class Role {
        SUPER_ADMIN, ADMIN, MOD
    }
}