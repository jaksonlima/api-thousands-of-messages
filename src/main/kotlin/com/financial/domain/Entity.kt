package com.financial.domain

import com.financial.domain.expections.AssertionConcern

abstract class Entity<ID : Identifier<*>>(
    private val id: ID,
    private val events: ArrayList<DomainEvent> = arrayListOf()
) : AssertionConcern {

    fun registryEvent(event: DomainEvent) {
        events.add(event)
    }

    fun domainEvents(): List<DomainEvent> {
        return events.toList()
    }

    fun id(): ID {
        return this.id
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as Entity<*>

        return id() == other.id()
    }

    override fun hashCode(): Int {
        return id().hashCode()
    }

}