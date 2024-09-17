package org.k4ssistant.kubeconfig.domain.model

import io.kubernetes.client.util.KubeConfig
import java.nio.file.Path

data class KubeConfigLocation(val kubeConfig: KubeConfig, val location: Path)
