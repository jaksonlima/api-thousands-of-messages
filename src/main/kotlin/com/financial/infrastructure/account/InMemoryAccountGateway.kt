package com.financial.infrastructure.account

import com.financial.domain.DomainEvent
import com.financial.domain.account.Account
import com.financial.domain.account.AccountGateway
import com.financial.domain.account.AccountID
import com.financial.infrastructure.observable.Publisher
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryAccountGateway(
    private val publisher: Publisher<DomainEvent>
) : AccountGateway {
    companion object {
        private val db: MutableMap<AccountID, Account> = ConcurrentHashMap()
    }

    override fun create(account: Account): Account {
        db.put(account.id(), account)

        account.domainEvents().forEach { publisher.publish(it) }

        return account
    }
}