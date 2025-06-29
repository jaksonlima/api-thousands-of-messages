package com.financial.domain.expections

interface AssertionConcern {
    fun <T : Any> assertArgumentNotNull(value: T?, lazyMessage: () -> String): T {
        if (value == null) {
            val message = lazyMessage()
            throw DomainException.with(message);
        }

        return value
    }

    fun assertArgumentNotEmpty(value: String?, lazyMessage: () -> String): String {
        if (value == null || value.isBlank()) {
            val message = lazyMessage()
            throw DomainException.with(message);
        }

        return value
    }

    fun <T : Any> assertArgumentNull(value: T?, lazyMessage: () -> String) {
        if (value != null) {
            val message = lazyMessage()
            throw DomainException.with(message);
        }
    }
}