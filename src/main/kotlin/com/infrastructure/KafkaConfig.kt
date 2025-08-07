package com.financial.infrastructure

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration(proxyBeanMethods = false)
class KafkaConfig(
    private val properties: KafkaProperties
) {

    @Bean
    fun startSendEmailsTopic(
    ): NewTopic {
        return NewTopic("start.send.emails", 2, 1)
    }

    @Bean
    fun processingSendEmailsTopic(
    ): NewTopic {
        return NewTopic("processing.send.email", 2, 1)
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
            ProducerConfig.BATCH_SIZE_CONFIG to 32768,         // 32 KB
            ProducerConfig.LINGER_MS_CONFIG to 10,             // 10 ms
            ProducerConfig.BUFFER_MEMORY_CONFIG to 33554432    // 32 MB
        )

        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> =
        KafkaTemplate(producerFactory())

    @Bean
    fun kafkaListenerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.containerProperties.pollTimeout = properties.poolTimeout
        factory.consumerFactory = consumerFactory()
        return factory
    }

    private fun consumerFactory(): ConsumerFactory<String, Any> {
        return DefaultKafkaConsumerFactory(consumerConfigs())
    }

    private fun consumerConfigs(): Map<String, Any> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG to properties.autoCreateTopics,
        )
        return props
    }

    @Configuration(proxyBeanMethods = false)
    @ConfigurationProperties(prefix = "kafka")
    class KafkaProperties {
        var bootstrapServers: String = ""
        var poolTimeout: Long = 0
        var autoCreateTopics: Boolean = false
    }

}