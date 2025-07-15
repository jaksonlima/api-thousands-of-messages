package com.financial.domain.account

import java.util.Optional

interface AccountGateway {
    fun create(account: Account): Account
    fun getByIdAndDeletedAtIsNull(accountId: AccountID): Optional<Account>
}