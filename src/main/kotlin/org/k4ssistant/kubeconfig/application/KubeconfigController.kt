package org.k4ssistant.kubeconfig.application

import org.k4ssistant.kubeconfig.domain.KubeConfigLocation
import org.k4ssistant.kubeconfig.domain.KubeConfigConnectionRequest
import org.k4ssistant.kubeconfig.infrastruture.secondary.ClusterConnectorService
import org.k4ssistant.kubeconfig.infrastruture.secondary.FileLoaderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/kubeconfig")
class KubeconfigController(
    private val fileLoaderService: FileLoaderService,
    private val clusterConnectorService: ClusterConnectorService
) {

    @GetMapping
    fun getAll(): List<KubeConfigLocation> {
        return fileLoaderService.getAll()
    }

    @PostMapping("/add/content")
    fun saveContent(@RequestBody content: String) {
        fileLoaderService.saveContent(content)
    }

    @PostMapping("/add/file")
    fun saveFile(@RequestParam file: MultipartFile) {
        fileLoaderService.saveFile(file)
    }

    @PostMapping("/connect")
    fun connect(@RequestBody kubeConfigConnectionRequest: KubeConfigConnectionRequest) {
        clusterConnectorService.connect(kubeConfigConnectionRequest.location)
    }
}