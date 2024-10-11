package org.k4ssistant.pods.infrastructure.primary.stream.consumer

import org.k4ssistant.pods.infrastructure.secondary.PodWatcherService
import org.k4ssistant.shared.infrastructure.primary.stream.consumer.AbstractEventInternalConsumer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PodInternalEventConsumer(
    @Value("\${topics.internal.pod}") topic: String,
    private val podWatcherService: PodWatcherService
) : AbstractEventInternalConsumer<Boolean>(topic) {

    override fun handleMessage(message: Boolean) {
        podWatcherService.change(message)
    }
}