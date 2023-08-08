package com.sphirye.springtemplate.security

import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    @Autowired
    private lateinit var _customUserDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var _jwtTokenFilter: JwtTokenFilter

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors(withDefaults())
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            .headers { header ->
                header.frameOptions { frameOption -> frameOption.sameOrigin() }
            }

            .exceptionHandling { exceptionHandling ->
                exceptionHandling.authenticationEntryPoint { _, response, ex ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
                }

                exceptionHandling.accessDeniedHandler { _, response, ex ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
                }
            }

            // TODO: Fix authorization
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers(HttpMethod.POST, "/core/auth/**").permitAll()
                    .anyRequest().authenticated()
            }

            .addFilterBefore(_jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return object : BCryptPasswordEncoder() {
            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
                return rawPassword == encodedPassword
            }
        }
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return CustomAuthenticationManager(_customUserDetailsService)
    }
}