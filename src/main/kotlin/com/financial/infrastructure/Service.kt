package com.financial.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.sql.DataSource

@RestController
@RequestMapping(value = ["api"])
class Service(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val dataSource: DataSource,
    private val objectMapper: ObjectMapper,
    private val redisTemplate: RedisTemplate<String, String>
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(Service::class.java)
        const val DEDUP_KEY_PREFIX: String = "kafka:dedup:"
    }

    @GetMapping(
        value = ["/generate-emails"],
    )
    fun generateRegistries(
        @RequestParam("quantity", required = false, defaultValue = "10000") quantity: Int
    ): ResponseEntity<String> {
        generateEmails(quantity)
        log.info("generated emails [quantity: $quantity]")
        return ResponseEntity.ok().build()
    }

    @PostMapping(
        value = ["/start-send-emails"],
    )
    fun startSendEmails(): ResponseEntity<String> {
        this.kafkaTemplate.send("start.send.emails", "true")
        log.info("started [message: true] [topic: start.send.emails]")
        return ResponseEntity.ok().build()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @KafkaListener(
        concurrency = "1",
        containerFactory = "kafkaListenerFactory",
        topics = ["start.send.emails"],
        groupId = "start.send.emails.group",
        id = "start.send.emails.id",
        properties = ["auto.offset.reset=earliest"]
    )
    fun onMessageSendEmails(@Payload(required = false) payload: String?, metadata: ConsumerRecordMetadata) {
        log.info("send received [message: $payload] [topic: ${metadata.topic()}] [partition: ${metadata.partition()}]")

        if (payload?.trim('"') == "true") {
            CoroutineScope(Dispatchers.IO).launch {
                sendEmails()
            }
        }
    }

    @KafkaListener(
        concurrency = "1",
        containerFactory = "kafkaListenerFactory",
        topics = ["processing.send.email"],
        groupId = "processing.send.email.group",
        id = "processing.send.email.id",
        properties = ["auto.offset.reset=earliest"]
    )
    fun onMessageProcessingSendEmail(@Payload(required = false) payload: String?, metadata: ConsumerRecordMetadata) {
        log.info("processing received [message: $payload] [topic: ${metadata.topic()}] [partition: ${metadata.partition()}]")

        if (payload !== null) {
            val map = Json.readTree(payload, Map::class.java) as Map<String, Any?>
            val id = map["id"] as String

            val dedupKey = DEDUP_KEY_PREFIX + id

            val isNew = this.redisTemplate.opsForValue()
                .setIfAbsent(dedupKey, Instant.now().toString(), Duration.ofHours(10L))

            if (isNew == true) {
                log.info("Redis not exists [key: $dedupKey]")
                updateEmailsUpdatedAt(mutableListOf(map))
            }
        }
    }

    fun generateEmails(quantity: Int) {
        val sql = """
            INSERT INTO email (id, email, status, active, created_at, updated_at)
            VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, null)
        """.trimIndent()

        val statusList = listOf("success", "processing", "failure")

        dataSource.connection.use { conn ->
            conn.autoCommit = false

            try {
                conn.prepareStatement(sql).use { stmt ->
                    repeat(quantity) { i ->
                        val id = UUID.randomUUID().toString()
                        val email = "user$i@example.com"
                        val status = statusList.random()
                        val active = true

                        stmt.setObject(1, id)
                        stmt.setString(2, email)
                        stmt.setString(3, status)
                        stmt.setBoolean(4, active)

                        stmt.addBatch()
                    }
                    stmt.executeBatch()
                    conn.commit()
                }
            } catch (e: Exception) {
                log.error(e.message)
            }
        }
    }

    fun sendEmails() = runBlocking {
        var delayMillis = 1000L
        val multiplier = 2.0
        var totalRecordSent = 1000

        while (true) {
            val emails = emailsPagination(7, 1000, 0)
            if (emails.isEmpty()) break

            sendEmailsTopic(emails)

            delay(delayMillis.coerceAtMost(32000))

            delayMillis = (delayMillis * multiplier).toLong()

            log.info("Get Emails [total record sent: $totalRecordSent]")

            totalRecordSent += 1000
        }
    }

    fun emailsPagination(frequencyDay: Int, limit: Int, offset: Int): MutableList<Map<String, Any?>> {
        val sql = """
        SELECT id, email, status, active, created_at, updated_at
        FROM email
        WHERE active = TRUE
          AND (updated_at IS NULL OR updated_at <= NOW() - (? * INTERVAL '1 day'))
        LIMIT ? OFFSET ?
        """

        val emails = mutableListOf<Map<String, Any?>>()

        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, frequencyDay)
                stmt.setInt(2, limit)
                stmt.setInt(3, offset)

                val rs = stmt.executeQuery()
                while (rs.next()) {
                    val emailsToJson = mapOf(
                        "id" to rs.getString("id"),
                        "email" to rs.getString("email"),
                        "status" to rs.getString("status"),
                        "active" to rs.getBoolean("active"),
                        "createdAt" to rs.getTimestamp("created_at")?.toInstant()?.toString(),
                        "updatedAt" to rs.getTimestamp("updated_at")?.toInstant()?.toString()
                    )

                    emails.add(emailsToJson)
                }
            }
        }
        return emails
    }

    fun sendEmailsTopic(emails: MutableList<Map<String, Any?>>) {
        if (emails.isEmpty()) return;

        for (emailMap in emails) {
            val jsonString = objectMapper.writeValueAsString(emailMap)

            this.kafkaTemplate.send("processing.send.email", jsonString)
        }
    }

    fun updateEmailsUpdatedAt(emails: MutableList<Map<String, Any?>>) {
        if (emails.isEmpty()) return;

        val sql = "UPDATE email SET updated_at = CURRENT_TIMESTAMP WHERE id = ?"

        dataSource.connection.use { conn ->
            conn.autoCommit = false
            conn.prepareStatement(sql).use { stmt ->
                for (emailMap in emails) {
                    val id = emailMap["id"] as? String
                    stmt.setObject(1, id)
                    stmt.addBatch()
                }
                stmt.executeBatch()
            }
            conn.commit()
        }
    }
}