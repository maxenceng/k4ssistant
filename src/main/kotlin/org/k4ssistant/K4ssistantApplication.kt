package org.k4ssistant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K4ssistantApplication

fun main(args: Array<String>) {
    runApplication<K4ssistantApplication>(*args)
}
