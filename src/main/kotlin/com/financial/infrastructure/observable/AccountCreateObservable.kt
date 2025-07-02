package com.financial.infrastructure.observable

import com.financial.application.account.AccountCreateUseCase
import com.financial.infrastructure.account.models.CreateRequest
import com.financial.infrastructure.kafka.producers.AccountCreateProducer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AccountCreateObservable(
    private val accountCreateUseCase: AccountCreateUseCase,
    private val accountCreateProducer: AccountCreateProducer
) {

    companion object {
        private val log = LoggerFactory.getLogger(AccountCreateObservable::class.java)
    }

    fun mediate(account: CreateRequest) {
        val result = this.accountCreateUseCase.execute(account)
//        this.accountCreateProducer.createAccount()
    }
}