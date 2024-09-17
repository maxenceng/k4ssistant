package org.k4ssistant.pods.stream.producer

import org.k4ssistant.shared.stream.producer.AbstractEventInternalProducer
import org.springframework.stereotype.Component

@Component
class PodWatcherProducer : AbstractEventInternalProducer<Boolean>("pod-internal")