package org.k4ssistant.shared.infrastructure.secondary.stream.producer

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.k4ssistant.shared.domain.EventProducer
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractEventInternalProducer<T>(private val topic: String) : EventProducer<T> {

    @Autowired
    private lateinit var kafkaProducer: KafkaProducer<String, T>

    @PostConstruct
    open fun init() {
        Runtime.getRuntime().addShutdownHook(Thread(::shutdown))
    }

    @PreDestroy
    open fun destroy() {
        shutdown()
    }

    private fun shutdown() {
        kafkaProducer.close()
    }

    override fun send(message: T) {
        val producerRecord = ProducerRecord<String, T>(topic, message)
        kafkaProducer.send(producerRecord)
    }
}