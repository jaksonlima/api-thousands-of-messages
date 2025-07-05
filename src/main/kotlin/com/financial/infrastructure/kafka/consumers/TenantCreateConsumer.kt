package com.financial.infrastructure.kafka.consumers

import com.financial.application.tenant.TenantCreateUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TenantCreateConsumer(
    private val tenantCreateUseCase: TenantCreateUseCase
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TenantCreateConsumer::class.java)
    }

    fun consumer(accountId: String) {
        try {
            log.info("Consumer message created: [accountId: ${accountId}]")
            val result = this.tenantCreateUseCase.execute(Input(accountId))
            log.info("Success tenant created: [tenantId: ${result.id()}]")
        } catch (e: Exception) {
            log.error("Failure consumer tenant created: [accountId: ${accountId}], error: [message: ${e.message}]")
        }
    }

    data class Input(val id: String) : TenantCreateUseCase.Input {
        override fun accountId(): String = this.id
    }
}