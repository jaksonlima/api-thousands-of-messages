package com.financial.infrastructure.account.models

import java.time.Instant

data class FindByIdResponse(
    val id: String,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
