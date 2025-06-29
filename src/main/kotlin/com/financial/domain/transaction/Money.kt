package com.financial.domain.transaction

import com.financial.domain.ValueObject
import java.util.*

data class Money(val amount: Double, var currency: Currency) : ValueObject {

    companion object {
        fun create(amount: Double): Money {
            return Money(amount, Currency.getInstance("BRL"))
        }
    }
}
