package org.k4ssistant.kubeconfig.infrastruture.primary

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.Configuration
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class ClientSetterService {
    fun setTo(client: ApiClient) {
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