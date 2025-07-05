package com.financial.domain.tenant

import com.financial.domain.Entity
import java.time.Instant

class TenantEvent(
    id: TenantEventID = TenantEventID(),
    val tenantId: TenantID = TenantID(),
    val eventType: EventType,
    val content: String,
    val createdAt: Instant,
) : Entity<TenantEventID>(id) {

    init {
        assertArgumentNotNull(id) { "'id' should not be null" }
        assertArgumentNotNull(tenantId) { "'tenantId' should not be null" }
        assertArgumentNotNull(eventType) { "'accountId' should not be null " }
        assertArgumentNotEmpty(content) { "'content' should not be null or empty " }
        assertArgumentNotNull(createdAt) { "'createdAt' should not be null" }
    }

    companion object {
        fun create(
            tenantId: TenantID,
            eventType: EventType,
            content: String,
        ): TenantEvent {
            val now = Instant.now()
            return TenantEvent(
                tenantId = tenantId,
                eventType = eventType,
                content = content,
                createdAt = now
            )
        }

        fun with(
            id: TenantEventID = TenantEventID(),
            tenantId: TenantID,
            eventType: EventType,
            content: String,
            createdAt: Instant,
        ): TenantEvent {
            return TenantEvent(
                id = id,
                tenantId = tenantId,
                eventType = eventType,
                content = content,
                createdAt = createdAt
            )
        }
    }

    fun isProcessing(): Boolean = EventType.PROCESSING == this.eventType

}