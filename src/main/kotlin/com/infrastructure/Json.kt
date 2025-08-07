package com.infrastructure

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.blackbird.BlackbirdModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.util.concurrent.Callable

enum class Json {
    INSTANCE;

    companion object {
        fun mapper(): ObjectMapper {
            return INSTANCE.mapper().copy()
        }

        fun <T> readTree(json: String, clazz: Class<T>): T {
            return invoke {
                val node = INSTANCE.mapper().readTree(json)
                if (node is TextNode) {
                    readTree(node.asText(), clazz)
                } else {
                    INSTANCE.mapper().convertValue(node, clazz)
                }
            }
        }

        private fun <T> invoke(callable: Callable<T>): T {
            try {
                return callable.call();
            } catch (e: Exception) {
                throw RuntimeException(e);
            }
        }

    }

    private fun mapper(): ObjectMapper {
        return Jackson2ObjectMapperBuilder.json()
            .dateFormat(StdDateFormat())
            .featuresToDisable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            )
            .modules(
                JavaTimeModule(),
                Jdk8Module(),
                BlackbirdModule(),
                KotlinModule.Builder().build()
            )
            .build()
    }

}