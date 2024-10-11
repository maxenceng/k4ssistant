package org.k4ssistant.pods.infrastructure.secondary

import org.k4ssistant.pods.infrastructure.secondary.stream.producer.PodInternalEventProducer
import org.k4ssistant.shared.infrastructure.secondary.AbstractTriggerService
import org.springframework.stereotype.Service

@Service
class PodTriggerService(podInternalEventProducer: PodInternalEventProducer) :
    AbstractTriggerService(podInternalEventProducer)