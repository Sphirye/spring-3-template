package com.sphirye.shared.web.aspect

import com.sphirye.springtemplate.service.tools.Constants
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Aspect
@Component
class PagedAspect {

    @Autowired
    private lateinit var response: HttpServletResponse

    @Around("@annotation(com.sphirye.shared.web.annotation.Paged)")
    fun injectValue(joinPoint: ProceedingJoinPoint): Any {
        val result = joinPoint.proceed()

        if (result is Page<*>) {
            response.addHeader(Constants.X_TOTAL_COUNT_HEADER, result.totalElements.toString())
            response.addHeader(Constants.X_TOTAL_PAGES_HEADER, result.totalPages.toString())
            response.addHeader(Constants.X_PAGE_SIZE_HEADER, result.size.toString())
        }

        return result
    }

}