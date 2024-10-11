package org.k4ssistant.shared.infrastructure.secondary

import org.k4ssistant.shared.domain.EventProducer

abstract class AbstractTriggerService(private val eventProducer: EventProducer<Boolean>) {
    fun startWatching() {
        eventProducer.send(true)
    }

    fun stopWatching() {
        eventProducer.send(false)
    }
}