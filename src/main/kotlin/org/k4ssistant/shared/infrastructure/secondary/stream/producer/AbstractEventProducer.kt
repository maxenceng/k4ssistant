package org.k4ssistant.shared.infrastructure.secondary.stream.producer

import org.k4ssistant.shared.domain.TopicResolver

abstract class AbstractEventProducer<T> : AbstractEventInternalProducer<T>(TopicResolver.getTopic<T>())