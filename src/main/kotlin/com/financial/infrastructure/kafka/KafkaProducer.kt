package com.financial.infrastructure.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : Producer {

    override fun send(topic: String, message: String) {
        this.kafkaTemplate.send(topic, message)
    }

}