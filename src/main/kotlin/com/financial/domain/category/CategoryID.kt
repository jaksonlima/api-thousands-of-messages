package com.financial.domain.category

import com.financial.domain.Identifier
import java.util.*

data class CategoryID(private val id: UUID = UUID.randomUUID()) : Identifier<UUID> {
    override fun value(): UUID {
        return this.id
    }
}
