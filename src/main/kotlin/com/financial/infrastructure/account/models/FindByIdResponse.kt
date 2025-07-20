package com.financial.infrastructure.account.models

import com.financial.application.account.AccountGetUseCase
import java.time.Instant

data class FindByIdResponse(
    val id: String,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(output: AccountGetUseCase.Output): FindByIdResponse {
            return FindByIdResponse(
                id = output.id(),
                name = output.name(),
                createdAt = output.createdAt(),
                updatedAt = output.updatedAt()
            )
        }
    }
}
