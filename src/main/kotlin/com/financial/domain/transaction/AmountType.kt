package com.financial.domain.transaction

import com.financial.domain.ValueObject
import com.financial.domain.expections.DomainException

enum class AmountType : ValueObject {
    CREDIT,
    DEBIT;

    companion object {
        fun from(amount: String): AmountType = when (amount) {
            "credit" -> CREDIT
            "debit" -> DEBIT
            else -> {
                throw DomainException.with("'amount type' not found")
            }
        }
    }
}