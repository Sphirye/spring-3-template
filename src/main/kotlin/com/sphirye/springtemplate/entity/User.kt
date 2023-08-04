package com.sphirye.springtemplate.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "`USER`")
class User(
    @Id
    var id: Long? = null,

    @Column
    var username: String? = null,

    @Column
    @NotNull
    var password: String? = null
)