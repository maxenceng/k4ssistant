package org.k4ssistant.pods.infrastructure.secondary.stream.producer

import org.k4ssistant.shared.infrastructure.secondary.stream.producer.AbstractEventInternalProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PodInternalEventProducer(@Value("\${topics.internal.pod}") topic: String) : AbstractEventInternalProducer<Boolean>(topic)