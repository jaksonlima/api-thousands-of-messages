package com.financial.application.category

import com.financial.application.UseCaseTest
import com.financial.domain.category.Category
import com.financial.domain.category.CategoryGateway
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CategoryCreateUseCaseTest : UseCaseTest() {

    @InjectMocks
    private lateinit var useCase: CategoryCreateUseCase

    @Mock
    private lateinit var gateway: CategoryGateway

    @Test
    fun given_when_then() {
        //given
        val expectedName = "car"

        //when
        `when`(this.gateway.create(any()))
            .thenAnswer { it.arguments[0] }

        //then
        val result = this.useCase.execute(Input(expectedName))

        val captor = argumentCaptor<Category>()
        verify(this.gateway, times(1)).create(captor.capture());

        assertNotNull(result.id())
        assertEquals(expectedName, captor.firstValue.name)
    }

    data class Input(
        val name: String,
    ) : CategoryCreateUseCase.Input {
        override fun name(): String = this.name
    }
}