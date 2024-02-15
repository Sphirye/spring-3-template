package com.sphirye.shared.web.resolver

import com.sphirye.shared.web.annotation.Pager
import com.sphirye.shared.web.utils.PageRequestBuilder
import org.springframework.core.MethodParameter
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class PageParamResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterAnnotations.any { it is Pager }
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): PageRequest {
        return PageRequestBuilder.build(webRequest)
    }
}