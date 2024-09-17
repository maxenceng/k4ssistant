package org.k4ssistant.shared.stream.producer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate

abstract class AbstractEventInternalProducer<T>(private val topic: String) : EventProducer<T> {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    override fun send(message: T) {
        kafkaTemplate.send(topic, message)
    }
}