package com.financial.domain.tenant

import com.financial.domain.UnitTest
import com.financial.domain.account.AccountID
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TenantTest : UnitTest() {

    @Test
    fun givenValidParams_whenCreate_shouldReturnValidIt() {
        //given
        val expectedAccountId = AccountID()

        //when
        val tenant = Tenant.create(expectedAccountId)

        //then
        assertNotNull(tenant.id())
        assertEquals(expectedAccountId, tenant.accountId)
        assertNotNull(tenant.createdAt)
    }

}