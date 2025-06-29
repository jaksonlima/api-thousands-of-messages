package com.financial.domain.wallet

import com.financial.domain.Identifier
import java.util.*

data class WalletID(private val id: UUID = UUID.randomUUID()) : Identifier<UUID> {

    override fun value(): UUID = this.id

    companion object {
        fun with(aId: String): WalletID = WalletID(UUID.fromString(aId))
    }
}
