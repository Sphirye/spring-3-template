package com.sphirye.springtemplate.service

import com.sphirye.springtemplate.entity.User
import com.sphirye.springtemplate.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired lateinit var userRepository: UserRepository



    fun findById(id: Long): User {
        return userRepository.getReferenceById(id)
    }

    fun existsById(id: Long): Boolean = userRepository.existsById(id)

}