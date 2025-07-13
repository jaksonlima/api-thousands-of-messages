package com.financial.infrastructure.kafka.producers

import com.fasterxml.jackson.databind.ObjectMapper
import com.financial.domain.account.AccountCreateEvent
import com.financial.infrastructure.configuration.annotations.AccountTopic
import com.financial.infrastructure.kafka.Producer
import com.financial.infrastructure.kafka.models.Topic
import org.springframework.stereotype.Component

@Component
class AccountCreateProducer(
    @AccountTopic
    private val topic: Topic,
    private val producer: Producer,
    private val objectMapper: ObjectMapper
) {

    fun createAccount(account: AccountCreateEvent) {
//        val result = objectMapper.writeValueAsString(account)
        this.producer.send(topic.name, account)
    }
}