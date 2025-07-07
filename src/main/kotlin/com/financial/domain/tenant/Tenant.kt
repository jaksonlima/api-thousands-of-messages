package com.financial.domain.tenant

import com.financial.domain.Aggregate
import com.financial.domain.account.AccountID
import java.time.Instant

class Tenant(
    id: TenantID = TenantID(),
    val accountId: AccountID,
    private val tenantEvents: ArrayList<TenantEvent> = ArrayList(),
    val createdAt: Instant,
    val updatedAt: Instant,
) : Aggregate<TenantID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null" }
        assertArgumentNotNull(accountId) { "'accountId' should not be null " }
        assertArgumentNotNull(tenantEvents) { "'tenantEvents' should not be null " }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null" }
        assertArgumentNotNull(updatedAt) { "'updatedAt' should not be null" }
    }

    companion object {
        fun create(
            accountId: AccountID
        ): Tenant {
            val now = Instant.now()
            return Tenant(
                accountId = accountId,
                createdAt = now,
                updatedAt = now
            )
        }

        fun with(
            id: TenantID = TenantID(),
            accountId: AccountID,
            tenantEvents: ArrayList<TenantEvent>,
            createdAt: Instant,
            updatedAt: Instant,
        ): Tenant {
            return Tenant(
                id = id,
                accountId = accountId,
                tenantEvents = tenantEvents,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    fun tenantEvents(): List<TenantEvent> {
        return this.tenantEvents.toList()
    }

    private fun createTenantEvent(event: EventType): Tenant {
        val accountId = this.accountId.value().toString()

        val tenantEvent = TenantEvent.create(
            this.id(),
            event,
            event.description.format(accountId)
        )

        this.tenantEvents.add(tenantEvent)

        return this;
    }


    fun createTenantEventCreated(): Tenant {
        return createTenantEvent(EventType.CREATED)
    }

    fun createTenantEventProcessing(): Tenant {
        return createTenantEvent(EventType.PROCESSING)
    }


    fun createTenantEventAccountNotFound(): Tenant {
        return createTenantEvent(EventType.ACCOUNT_NOT_FOUND)
    }

}