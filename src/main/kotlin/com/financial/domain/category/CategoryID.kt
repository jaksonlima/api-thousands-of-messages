package com.financial.domain.category

import com.financial.domain.Identifier
import java.util.*

data class CategoryID(private val id: UUID = UUID.randomUUID()) : Identifier<UUID> {

    companion object {
        fun with(aId: String): CategoryID = CategoryID(UUID.fromString(aId))
    }

    override fun value(): UUID {
        return this.id
    }

    override fun toString(): String {
        return this.id.toString()
    }
}
