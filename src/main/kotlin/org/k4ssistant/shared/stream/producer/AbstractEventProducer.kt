package org.k4ssistant.shared.stream.producer

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.google.gson.reflect.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate

abstract class AbstractEventProducer<T> : EventProducer<T> {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    override fun send(message: T) {
        kafkaTemplate.send(topic(), message)
    }

    private fun topic(): String = PropertyNamingStrategies.KebabCaseStrategy().translate(
        object : TypeToken<T>() {}.javaClass.name.toString()
    )
}