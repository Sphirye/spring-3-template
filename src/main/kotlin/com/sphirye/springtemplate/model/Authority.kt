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
    var users: MutableList<User>? = null,

    @Column(nullable = false)
    var enabled: Boolean,

) {
    enum class Role {
        SUPER_ADMIN, ADMIN, MOD
    }
}