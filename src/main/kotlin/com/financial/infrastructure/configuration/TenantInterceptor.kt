package com.financial.infrastructure.configuration

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

private const val X_TENANT_ID = "X-Tenant-ID"

class TenantInterceptor : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val tenantId = request.getHeader(X_TENANT_ID)

        if (tenantId != null && !tenantId.isEmpty()) {
//            TenantContext.setTenantId(tenantId);
//            tenantSchemaService.initTenantSchema(tenantId);
        }

        return true;
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
//        TenantContext.clear();
    }

}