package com.financial.infrastructure.account

import com.financial.domain.DomainEvent
import com.financial.domain.account.Account
import com.financial.domain.account.AccountGateway
import com.financial.domain.account.AccountID
import com.financial.infrastructure.account.persistence.AccountJpaEntity
import com.financial.infrastructure.account.persistence.AccountRepository
import com.financial.infrastructure.observable.Publisher
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountPostgresGateway(
    private val accountRepository: AccountRepository,
    private val publisher: Publisher<DomainEvent>
) : AccountGateway {

    override fun create(account: Account): Account {
        this.accountRepository.save(AccountJpaEntity.from(account))

        account.domainEvents().forEach { publisher.publish(it) }

        return account
    }

    override fun getByIdAndDeletedAtIsNull(accountId: AccountID): Optional<Account> {
        return this.accountRepository.getByIdAndDeletedAtIsNull(accountId.value().toString())
            .map { it.toDomain() }
    }

}