package com.financial.domain

import java.time.Instant

interface DomainEvent {
    fun occurredOn(): Instant
}