package org.k4ssistant.kubeconfig.controller

import org.k4ssistant.kubeconfig.domain.model.KubeConfigLocation
import org.k4ssistant.kubeconfig.domain.request.KubeConfigConnectionRequest
import org.k4ssistant.kubeconfig.service.ClusterConnectorService
import org.k4ssistant.kubeconfig.service.FileManagementService
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
    private val fileManagementService: FileManagementService,
    private val clusterConnectorService: ClusterConnectorService
) {

    @GetMapping
    fun getAll(): List<KubeConfigLocation> {
        return fileManagementService.getAll()
    }

    @PostMapping("/add/content")
    fun saveContent(@RequestBody content: String) {
        fileManagementService.saveContent(content)
    }

    @PostMapping("/add/file")
    fun saveFile(@RequestParam file: MultipartFile) {
        fileManagementService.saveFile(file)
    }

    @PostMapping("/connect")
    fun connect(@RequestBody kubeConfigConnectionRequest: KubeConfigConnectionRequest) {
        clusterConnectorService.connect(kubeConfigConnectionRequest.location)
    }
}