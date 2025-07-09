package com.financial.domain.tenant

enum class EventType(val description: String) {
    PROCESSING("processing [account-id: %s]"),
    CREATED("created [account-id: %s]"),
}