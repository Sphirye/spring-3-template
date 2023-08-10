package com.sphirye.springtemplate.security

import com.sphirye.springtemplate.model.UserIdentity
import com.sphirye.springtemplate.security.util.JwtTokenUtil
import com.sphirye.springtemplate.service.UserService
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

    @Autowired
    private lateinit var _jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var _userService: UserService

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val jwtBearer = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (jwtBearer == null || jwtBearer.isEmpty() || !jwtBearer.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        val token = _jwtTokenUtil.resolveTokenFrom(jwtBearer)

        _jwtTokenUtil.validate(token)

        val user = _userService.findById(_jwtTokenUtil.getSubject(token).toLong())

        val auth = UsernamePasswordAuthenticationToken(
            UserIdentity(email = user.email!!, id = user.id!!), user.password
        )

        auth.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = auth

        chain.doFilter(request, response)
    }
}