package com.sphirye.springtemplate.model

import jakarta.persistence.*

@Entity
@Table(name = "`USER`")
class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var username: String? = null,

    var email: String? = null,

    var password: String? = null,

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    var authorities: MutableSet<Authority> = mutableSetOf(),
)