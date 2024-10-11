package org.k4ssistant.shared.infrastructure.primary.stream.consumer

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.k4ssistant.shared.domain.EventConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean


abstract class AbstractEventInternalConsumer<T>(private val topic: String) : Runnable, EventConsumer<T> {

    @Value("\${kafka.polling.timeout}")
    private lateinit var pollingTimeout: Number

    @Autowired
    private lateinit var kafkaConsumer: KafkaConsumer<String, T>

    private val closed = AtomicBoolean(false)

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    open fun init() {
        Runtime.getRuntime().addShutdownHook(Thread(::shutdown))
    }

    @PreDestroy
    open fun destroy() {
        shutdown()
    }

    private fun shutdown() {
        closed.set(true)
        kafkaConsumer.wakeup()
    }

    override fun run() {
        try {
            kafkaConsumer.subscribe(listOf(topic))
            while (!closed.get()) {
                val records: ConsumerRecords<String, T> = kafkaConsumer.poll(Duration.ofMillis(pollingTimeout.toLong()))
                records.forEach(::handle)
                kafkaConsumer.commitSync()
            }
        } catch (e: WakeupException) {
            if (!closed.get()) {
                throw e
            }
        } catch (e: Exception) {
            logger.error("An error occured while polling for $topic: $e")
        } finally {
            kafkaConsumer.close()
        }
    }

    private fun handle(consumerRecord: ConsumerRecord<String, T>) {
        logger.debug("Received message: {}", consumerRecord)
        handleMessage(consumerRecord.value())
    }
}