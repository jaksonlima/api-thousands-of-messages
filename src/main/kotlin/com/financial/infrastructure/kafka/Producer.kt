package com.financial.infrastructure.kafka

interface Producer {
    fun send(topic: String, message: String)
}