package com.sphirye.springtemplate.repository

import com.sphirye.springtemplate.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>{

    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean

}