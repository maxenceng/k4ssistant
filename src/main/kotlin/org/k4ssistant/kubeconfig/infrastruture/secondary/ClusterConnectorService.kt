package org.k4ssistant.kubeconfig.infrastruture.secondary

import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import org.k4ssistant.kubeconfig.infrastruture.primary.ClientSetterService
import org.springframework.stereotype.Service
import java.io.FileReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit


@Service
class ClusterConnectorService(private val clientSetterService: ClientSetterService) {
    fun connect(location: Path) {
        val kubeConfig = FileReader(location.toFile())
        // loading the out-of-cluster config, a kubeconfig from file-system
        val client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(kubeConfig)).build()

        clientSetterService.setTo(client)
    }
}