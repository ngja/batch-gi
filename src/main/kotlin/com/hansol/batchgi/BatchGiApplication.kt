package com.hansol.batchgi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BatchGiApplication

fun main(args: Array<String>) {
    runApplication<BatchGiApplication>(*args)
}
