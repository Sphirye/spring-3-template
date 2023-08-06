package com.sphirye.springtemplate.security

import com.sphirye.springtemplate.model.UserIdentity
import com.sphirye.springtemplate.repository.UserRepository
import com.sphirye.springtemplate.security.util.JwtTokenUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class JwtTokenFilter: OncePerRequestFilter() {

    @Autowired lateinit var jwtTokenUtil: JwtTokenUtil
    @Autowired lateinit var userRepository: UserRepository

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        val jwtToken = authorizationHeader.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].trim { it <= ' ' }

        jwtTokenUtil.validate(jwtToken)

        val user = userRepository.getReferenceById(jwtTokenUtil.getSubject(jwtToken).toLong())

        val authentication = UsernamePasswordAuthenticationToken(
            UserIdentity(email = user.email!!, id = user.id!!), user.password
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication

        chain.doFilter(request, response)
    }
}