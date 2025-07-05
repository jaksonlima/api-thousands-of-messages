package com.financial.domain.tenant

enum class EventType(val description: String) {
    PROCESSING("processing [account-id: %id]"),
    CREATED("created [account-id: %id]"),
    ACCOUNT_NOT_FOUND("account not found or deleted: [account-id: %id]")
}