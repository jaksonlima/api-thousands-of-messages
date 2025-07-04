package com.financial.infrastructure.configuration

import com.financial.domain.DomainEvent
import com.financial.infrastructure.observable.Publisher
import com.financial.infrastructure.observable.domainevent.AccountCreateSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.time.Clock


@Configuration
@ComponentScan("com.financial.infrastructure")
class WebServerConfig {

    @Bean
    fun clock(): Clock {
        return Clock.systemUTC()
    }

    @Bean
    fun domainEventPublish(
        accountCreateSubscriber: AccountCreateSubscriber
    ): Publisher<DomainEvent> {
        return Publisher(arrayListOf(accountCreateSubscriber))
    }
}