package com.hansol.batchgi

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BatchGiApplication

val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    runApplication<BatchGiApplication>(*args)
}
