package com.financial.infrastructure.kafka

interface Producer {
    fun <T> send(topic: String, message: T)
}