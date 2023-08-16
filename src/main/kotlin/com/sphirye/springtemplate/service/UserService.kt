package com.sphirye.springtemplate.service

import com.sphirye.springtemplate.model.User
import com.sphirye.springtemplate.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService {

    @Autowired
    private lateinit var _userRepository: UserRepository

    fun create(user: User): User {
        user.id = null
        return _userRepository.save(user)
    }

    fun findById(id: Long): User {
        return _userRepository.getReferenceById(id)
    }

    fun findByEmail(email: String): User {
        if (!existsByEmail(email)) {
            //Todo: implement http exceptions
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "$email email not found")
        }
        return _userRepository.findByEmail(email)
    }

    fun existsById(id: Long): Boolean {
        return _userRepository.existsById(id)
    }

    fun existsByEmail(email: String): Boolean {
        return _userRepository.existsByEmail(email)
    }

}