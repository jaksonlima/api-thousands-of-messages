package com.financial.domain.transaction

interface TransactionGateway {
    fun create(transaction: Transaction): Transaction
}