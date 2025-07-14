package com.financial.domain.account

import com.financial.domain.DomainEvent
import java.time.Instant

data class AccountCreateEvent(
    val accountId: String,
    val createdAt: Instant,
    val occurredOn: Instant
) : DomainEvent {
    override fun occurredOn(): Instant = this.occurredOn
}
