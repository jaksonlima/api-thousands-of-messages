package com.financial.domain.account

interface AccountGateway {
    fun create(account: Account): Account
}