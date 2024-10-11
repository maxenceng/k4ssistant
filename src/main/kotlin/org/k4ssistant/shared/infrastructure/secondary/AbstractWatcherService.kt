package org.k4ssistant.shared.infrastructure.secondary

import com.google.gson.reflect.TypeToken
import io.kubernetes.client.common.KubernetesListObject
import io.kubernetes.client.common.KubernetesObject
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Watch
import okhttp3.Call
import org.k4ssistant.shared.domain.EventProducer
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean


abstract class AbstractWatcherService<T : KubernetesObject, K : KubernetesListObject>(private val eventProducer: EventProducer<T>) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val isStopped = AtomicBoolean(false)

    fun change(status: Boolean) {
        if (status) start()
        else stop()
    }

    private fun start() {
        isStopped.set(true)
        watch()
    }

    private fun stop() {
        logger.debug("Stopping watch job on {}", this::class.java)
        isStopped.set(false)
    }

    fun watch() {
        val api = CoreV1Api()
        val client = Configuration.getDefaultApiClient()
        var resourceVersion: String? = null
        while (true) {
            if (isStopped.get()) {
                logger.debug("Stopped watch job on {}", this::class.java)
                break
            }
            // Get a fresh list whenever we need to resync
            if (resourceVersion == null) {
                val podList = list(api, resourceVersion)
                resourceVersion = podList.metadata!!.resourceVersion
            }
            while (true) {
                try {
                    logger.debug("Creating watch on {}", this::class.java)
                    val watch = Watch.createWatch<T>(
                        client,
                        watchList(api, resourceVersion),
                        object : TypeToken<Watch.Response<T>>() {}.type
                    )
                    for (event in watch) {
                        val meta = event.`object`.metadata
                        when (event.type) {
                            "BOOKMARK" -> resourceVersion = meta!!.resourceVersion
                            "ADDED", "MODIFIED", "DELETED" -> {
                                logger.debug(event.type)
                                eventProducer.send(event.`object`)
                            }
                            else -> logger.warn("Unknown event type: ${event.type}")
                        }
                    }
                } catch (ex: ApiException) {
                    resourceVersion = null
                    break
                }
            }
        }
    }

    protected abstract fun list(api: CoreV1Api, resourceVersion: String?): K

    protected abstract fun watchList(api: CoreV1Api, resourceVersion: String?): Call
}