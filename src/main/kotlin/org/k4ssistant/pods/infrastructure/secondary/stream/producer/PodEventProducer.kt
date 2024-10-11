package org.k4ssistant.pods.infrastructure.secondary.stream.producer

import io.kubernetes.client.openapi.models.V1Pod
import org.k4ssistant.shared.infrastructure.secondary.stream.producer.AbstractEventInternalProducer
import org.k4ssistant.shared.infrastructure.secondary.stream.producer.AbstractEventProducer
import org.springframework.stereotype.Component

@Component
class PodEventProducer : AbstractEventProducer<V1Pod>()