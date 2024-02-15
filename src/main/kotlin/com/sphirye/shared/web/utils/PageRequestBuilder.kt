package com.sphirye.shared.web.utils

import com.sphirye.springtemplate.service.tools.Constants
import org.springframework.data.domain.PageRequest
import org.springframework.web.context.request.NativeWebRequest

object PageRequestBuilder {

    public fun build(request: NativeWebRequest): PageRequest {

        val page = request.getParameter("page")?.toIntOrNull() ?: Constants.API_REQUEST_DEFAULT_PAGE
        val pageSize = request.getParameter("pageSize")?.toIntOrNull() ?: Constants.API_REQUEST_DEFAULT_PAGE_SIZE

        return PageRequest.of(page, pageSize)
    }
}