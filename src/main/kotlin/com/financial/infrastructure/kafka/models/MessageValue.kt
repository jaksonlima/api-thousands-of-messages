package com.financial.infrastructure.kafka.models

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageValue<T>(
    @JsonProperty("payload") val payload: T
)
