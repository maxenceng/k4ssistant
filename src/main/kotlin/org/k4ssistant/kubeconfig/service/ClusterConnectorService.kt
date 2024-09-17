package org.k4ssistant.kubeconfig.service

import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import org.springframework.stereotype.Service
import java.io.FileReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit


@Service
class ClusterConnectorService {
    fun connect(location: Path) {
        val kubeConfig = FileReader(location.toFile())
        // loading the out-of-cluster config, a kubeconfig from file-system
        val client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(kubeConfig)).build()

        // set the global default api-client to the out-of-cluster one from above
        Configuration.setDefaultApiClient(client)

        // infinite timeout
        val httpClient = client.httpClient
            .newBuilder()
            .readTimeout(0, TimeUnit.SECONDS)
            .build()
        client.setHttpClient(httpClient)
    }
}