package org.k4ssistant.shared.domain

interface EventProducer<T> {
    fun send(message: T)
}