package com.sphirye.springtemplate.service

import com.sphirye.springtemplate.model.Authority
import com.sphirye.springtemplate.repository.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorityService {

    @Autowired
    private lateinit var _authorityRepository: AuthorityRepository

    @Autowired
    private lateinit var _userService: UserService

    fun create(authority: Authority): Authority {
        return _authorityRepository.save(authority)
    }

    fun findByRole(role: Authority.Role): Authority {
        return _authorityRepository.getReferenceById(role)
    }

    fun existsByRole(role: Authority.Role): Boolean {
        return _authorityRepository.existsById(role)
    }

}