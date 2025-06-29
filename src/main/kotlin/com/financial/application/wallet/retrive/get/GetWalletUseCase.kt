package com.financial.application.wallet.retrive.get

import com.financial.application.UseCase
import com.financial.domain.expections.NotFoundException
import com.financial.domain.wallet.Wallet
import com.financial.domain.wallet.WalletGateway
import com.financial.domain.wallet.WalletID
import java.time.Instant

class GetWalletUseCase(
    private val walletGateway: WalletGateway
) : UseCase<String, GetWalletUseCase.Output> {

    override fun execute(input: String): Output {
        val walletId = WalletID.with(input)

        val wallet = this.walletGateway.findById(walletId)
            .orElseThrow { NotFoundException.with(Wallet::class, walletId) }

        return StdOutput(
            id = wallet.id().value().toString(),
            name = wallet.name,
            active = wallet.active,
            createdAt = wallet.createdAt,
            updatedAt = wallet.updatedAt,
            deletedAt = wallet.deletedAt
        );
    }

    interface Output {
        fun id(): String;
        fun name(): String;
        fun active(): Boolean;
        fun createdAt(): Instant;
        fun updatedAt(): Instant;
        fun deletedAt(): Instant? = null
    }

    data class StdOutput(
        val id: String,
        val name: String,
        val active: Boolean,
        val createdAt: Instant,
        val updatedAt: Instant,
        val deletedAt: Instant? = null
    ) : Output {
        override fun id(): String = this.id
        override fun name(): String = this.name
        override fun active(): Boolean = this.active
        override fun createdAt(): Instant = this.createdAt;
        override fun updatedAt(): Instant = this.updatedAt
        override fun deletedAt(): Instant? = this.deletedAt
    }

}