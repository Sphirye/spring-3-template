package com.sphirye.springtemplate.security

import com.sphirye.springtemplate.security.util.JwtTokenUtil
import com.sphirye.springtemplate.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
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

        val token = resolveToken(request)

        if (token == null) {
            chain.doFilter(request, response)
            return
        }

        _jwtTokenUtil.validate(token)

        val details = _jwtTokenUtil.resolveUserDetailsFromToken(token)
        val user = _userService.findById(details!!.id!!)
        val auth = UsernamePasswordAuthenticationToken(user.email, user.password, null)
        auth.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = auth

        chain.doFilter(request, response)
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        if (authHeader.isNullOrEmpty()) { return null }

        return authHeader
            .split(" ".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()[1]
            .trim { it <= ' ' }
    }
}