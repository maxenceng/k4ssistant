package org.k4ssistant.pods.stream.producer

import io.kubernetes.client.openapi.models.V1Pod
import org.k4ssistant.shared.stream.producer.AbstractEventProducer
import org.springframework.stereotype.Component

@Component
class PodEventProducer : AbstractEventProducer<V1Pod>()