package com.financial.domain

import com.financial.domain.account.AccountID
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletID
import java.time.Instant

object Fixture {

    fun john(): Wallet {
        val now = Instant.now()
        return Wallet.with(
            WalletID(),
            "john",
            true,
            AccountID(),
            now,
            now,
            null
        )
    }

}