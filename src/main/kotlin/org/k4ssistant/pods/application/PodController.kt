package org.k4ssistant.pods.application

import org.k4ssistant.pods.infrastructure.secondary.PodTriggerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pods")
class PodController(private val podTriggerService: PodTriggerService) {

    @PostMapping
    fun startWatching() {
        podTriggerService.startWatching()
    }

    @PostMapping
    fun stopWatching() {
        podTriggerService.stopWatching()
    }
}