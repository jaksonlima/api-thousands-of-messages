package com.financial.infrastructure

import com.financial.infrastructure.configuration.WebServerConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Main

val LOG: Logger = LoggerFactory.getLogger(Main::class.java)

fun main(args: Array<String>) {
    LOG.info("[step:to-be-init] [id:1] Inicializando o Spring");
    runApplication<WebServerConfig>(*args)
    LOG.info("[step:inittialized] [id:2] Spring inicializado..");
}
