package org.k4ssistant.pods.service

import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1PodList
import okhttp3.Call
import org.k4ssistant.pods.stream.producer.PodEventProducer
import org.k4ssistant.shared.service.AbstractWatcherService
import org.springframework.stereotype.Service


@Service
class PodWatcherService(podEventProducer: PodEventProducer) : AbstractWatcherService<V1Pod, V1PodList>(podEventProducer) {

    override fun list(api: CoreV1Api, resourceVersion: String?): V1PodList = api.listPodForAllNamespaces(
        true,
        null,
        null,
        null,
        null,
        "false",
        resourceVersion,
        null,
        null,
        null
    )

    override fun watchList(api: CoreV1Api, resourceVersion: String?): Call = api.listPodForAllNamespacesCall(
        true,
        null,
        null,
        null,
        null,
        "false",
        resourceVersion,
        null,
        10,
        true,
        null
    )
}