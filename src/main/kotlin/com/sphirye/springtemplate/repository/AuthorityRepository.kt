package com.sphirye.springtemplate.repository

import com.sphirye.springtemplate.model.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository : JpaRepository<Authority, Authority.Role> {
}