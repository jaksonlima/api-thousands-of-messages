package com.financial.infrastructure.configuration.multitenant

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Component
class TenantInterceptor : HandlerInterceptor {

    companion object {
        private const val TENANT_HEADER: String = "x-tenant"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        Optional.ofNullable(request.getHeader(TENANT_HEADER))
            .map(String::lowercase)
            .ifPresent(TenantContext::setCurrentTenant);

        return true;
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        TenantContext.clear()
    }
}