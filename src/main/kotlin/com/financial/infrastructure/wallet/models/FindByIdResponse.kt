package com.financial.infrastructure.wallet.models

import com.financial.application.wallet.retrive.get.GetWalletUseCase
import java.time.Instant

data class FindByIdResponse(
    val id: String,
    val name: String,
    val active: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null
) : GetWalletUseCase.Output {
    companion object {
        fun from(output: GetWalletUseCase.Output): FindByIdResponse {
            return FindByIdResponse(
                id = output.id(),
                name = output.name(),
                active = output.active(),
                createdAt = output.createdAt(),
                updatedAt = output.updatedAt(),
                deletedAt = output.deletedAt()
            )
        }
    }

    override fun id(): String = this.id
    override fun name(): String = this.name
    override fun active(): Boolean = this.active
    override fun createdAt(): Instant = this.createdAt;
    override fun updatedAt(): Instant = this.updatedAt
    override fun deletedAt(): Instant? = this.deletedAt
}