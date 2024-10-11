package org.k4ssistant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@SpringBootApplication
class K4ssistantApplication

fun main(args: Array<String>) {
    runApplication<K4ssistantApplication>(*args)
}
