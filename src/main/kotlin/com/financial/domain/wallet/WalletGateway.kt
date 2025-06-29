package com.financial.domain.wallet

import java.util.Optional

interface WalletGateway {
    fun create(wallet: Wallet): Wallet
    fun findById(walletID: WalletID): Optional<Wallet>
}