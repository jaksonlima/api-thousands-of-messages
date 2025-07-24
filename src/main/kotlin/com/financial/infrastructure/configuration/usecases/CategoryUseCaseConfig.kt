package com.financial.infrastructure.configuration.usecases

import com.financial.application.category.CategoryCreateUseCase
import com.financial.domain.category.CategoryGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class CategoryUseCaseConfig(
    val categoryGateway: CategoryGateway
) {

    @Bean
    fun categoryCreateUseCase(): CategoryCreateUseCase {
        return CategoryCreateUseCase(this.categoryGateway)
    }
}