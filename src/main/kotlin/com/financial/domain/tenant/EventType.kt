package com.financial.domain.tenant

enum class EventType(val description: String) {
    PROCESSING("processing [account-id: %s]"),
    CREATED("created [account-id: %s]"),
    ACCOUNT_NOT_FOUND("account not found or deleted: [account-id: %s]")
}