package org.k4ssistant.shared.stream.producer

interface EventProducer<T> {
    fun send(message: T)
}