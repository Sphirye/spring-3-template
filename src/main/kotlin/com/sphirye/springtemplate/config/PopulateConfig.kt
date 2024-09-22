package com.sphirye.springtemplate.config

import com.sphirye.springtemplate.model.Authority
import com.sphirye.springtemplate.repository.AuthorityRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PopulateConfig {


    @Autowired
    private lateinit var _authorityRepository: AuthorityRepository

    @PostConstruct
    fun init() {
        Authority.Role.values().forEach {
            if (!_authorityRepository.existsById(it)) {
                _authorityRepository.save(Authority(id = it))
            }
        }
    }

}