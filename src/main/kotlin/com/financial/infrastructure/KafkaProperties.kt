package com.financial.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "kafka")
class KafkaProperties {
    var bootstrapServers: String = ""
    var poolTimeout: Long = 0
    var autoCreateTopics: Boolean = false
}