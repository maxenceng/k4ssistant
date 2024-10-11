package org.k4ssistant.shared.domain

interface EventConsumer<T> {
    fun handleMessage(message: T)
}