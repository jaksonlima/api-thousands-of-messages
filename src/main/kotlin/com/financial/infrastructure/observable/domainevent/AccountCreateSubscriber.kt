package com.financial.infrastructure.observable.domainevent

import com.financial.domain.DomainEvent
import com.financial.domain.account.AccountCreateEvent
import com.financial.infrastructure.kafka.producers.AccountCreateProducer
import com.financial.infrastructure.observable.Subscriber
import org.springframework.stereotype.Component

@Component
class AccountCreateSubscriber(
    private val accountCreateProducer: AccountCreateProducer
) : Subscriber<DomainEvent> {

    override fun test(ev: DomainEvent): Boolean {
        return ev is AccountCreateEvent
    }

    override fun onEvent(ev: DomainEvent) {
        if (ev is AccountCreateEvent) {
            this.accountCreateProducer.createAccount(ev);
        }
    }

}