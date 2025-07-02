package com.financial.infrastructure

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["test-topic"], brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class KafkaIntegrationTest {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    private val messageReceived = AtomicReference<String>()
    private val latch = CountDownLatch(1)

    @KafkaListener(topics = ["test-topic"], groupId = "test-group")
    fun listen(record: ConsumerRecord<String, String>) {
        messageReceived.set(record.value())
        latch.countDown()
    }

    @Test
    fun `deve enviar e receber mensagem no Kafka`() {
        val mensagem = "mensagem de teste"
        kafkaTemplate.send("test-topic", mensagem)

        val consumido = latch.await(5, TimeUnit.SECONDS)
        assertTrue(consumido, "A mensagem não foi consumida a tempo")
        assertEquals(mensagem, messageReceived.get())
    }

//    @Service
//    class MyKafkaProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {
//
//        fun sendMessage(topic: String, message: String) {
//            kafkaTemplate.send(topic, message)
//            println("Mensagem enviada: $message")
//        }
//    }

//    @Component
//    class MyKafkaConsumer {
//
//        @KafkaListener(topics = ["meu-topico"], groupId = "sample-group")
//        fun listen(record: ConsumerRecord<String, String>) {
//            println("Mensagem recebida: ${record.value()}")
//        }
//    }
}