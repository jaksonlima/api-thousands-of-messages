package com.financial.domain.category

import com.financial.domain.UnitTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class CategoryTest : UnitTest() {

    @Test
    fun givenValidParams_whenCreate_shouldReturnValidIt() {
        //given
        val expectedName = "car"

        //when
        val category = Category.create(expectedName)

        //then
        assertNotNull(category.id())
        assertEquals(expectedName, category.name)
        assertNotNull(category.createdAt)
        assertNotNull(category.updatedAt)
    }
}