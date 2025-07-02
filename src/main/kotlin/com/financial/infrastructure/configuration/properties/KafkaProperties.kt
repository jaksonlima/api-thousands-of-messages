package com.financial.infrastructure.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka")
class KafkaProperties {
    var bootstrapServers: String = ""
    var poolTimeout: Long = 0
    var autoCreateTopics: Boolean = false
}