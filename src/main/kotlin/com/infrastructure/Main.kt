package com.infrastructure

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Main

private val log = LoggerFactory.getLogger(Main::class.java)

fun main(args: Array<String>) {
    log.info("[step:to-be-init] [id:1] Inicializando o Spring");
    runApplication<Main>(*args)
    log.info("[step:inittialized] [id:2] Spring inicializado..");
}

