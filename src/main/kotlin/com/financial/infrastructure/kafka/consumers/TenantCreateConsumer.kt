package com.financial.infrastructure.kafka.consumers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.financial.application.account.AccountGetUseCase
import com.financial.application.tenant.TenantCreateUseCase
import com.financial.domain.account.AccountCreateEvent
import com.financial.infrastructure.kafka.models.MessageValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class TenantCreateConsumer(
    private val tenantCreateUseCase: TenantCreateUseCase,
    private val accountGetUseCase: AccountGetUseCase,
    private val objectMapper: ObjectMapper
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TenantCreateConsumer::class.java)
        private val ACCOUNT_MESSAGE_TYPE = object : TypeReference<MessageValue<AccountCreateEvent>>() {}
    }

    @KafkaListener(
        concurrency = "\${kafka.consumers.tenant.concurrency}",
        containerFactory = "kafkaListenerFactory",
        topics = ["\${kafka.consumers.tenant.topics}"],
        groupId = "\${kafka.consumers.tenant.group-id}",
        id = "\${kafka.consumers.tenant.id}",
        properties = ["auto.offset.reset=\${kafka.consumers.tenant.auto-offset-reset}"]
    )
    @RetryableTopic(
        backoff = Backoff(delay = 1000, multiplier = 2.0),
        attempts = "\${kafka.consumers.videos.max-attempts}",
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    fun onMessage(@Payload(required = false) payload: String?, metadata: ConsumerRecordMetadata) {
        if (payload == null) {
            log.info(
                "Message received from Kafka [topic:{}] [partition:{}] [offset:{}]: EMPTY",
                metadata.topic(),
                metadata.partition(),
                metadata.offset()
            )
            return
        }

        log.info(
            "Message received from Kafka [topic:{}] [partition:{}] [offset:{}]: {}",
            metadata.topic(),
            metadata.partition(),
            metadata.offset(),
            payload
        )

        val messagePayload = this.objectMapper.readValue(payload, ACCOUNT_MESSAGE_TYPE).payload

        this.accountGetUseCase.execute(messagePayload.accountId)
            .map { it.id() }
            .ifPresentOrElse(this.tenantCreateUseCase::execute) {
                log.warn("Account was not found ${messagePayload.accountId}")
            }
    }

    @DltHandler
    fun onDLTMessage(@Payload payload: String?, metadata: ConsumerRecordMetadata) {
        log.warn(
            "Message received from Kafka at DLT [topic:{}] [partition:{}] [offset:{}]: {}",
            metadata.topic(),
            metadata.partition(),
            metadata.offset(),
            payload
        )
    }

}