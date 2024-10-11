package org.k4ssistant.kubeconfig.domain

import io.kubernetes.client.util.KubeConfig
import java.nio.file.Path

data class KubeConfigLocation(val kubeConfig: KubeConfig, val location: Path)
