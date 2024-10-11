package org.k4ssistant.kubeconfig.infrastruture.secondary

import io.kubernetes.client.util.KubeConfig
import org.k4ssistant.kubeconfig.domain.HomeLinkedPath
import org.k4ssistant.kubeconfig.domain.KubeConfigLocation
import org.k4ssistant.kubeconfig.infrastruture.primary.FileManagementService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileReader
import java.nio.file.Path

@Service
class FileLoaderService(
    private val homeLinkedPath: HomeLinkedPath,
    private val fileManagementService: FileManagementService) {

    fun getAll(): List<KubeConfigLocation> {
        val assistantConfigs: List<Path> = fileManagementService.filesInFolder(getFolder())
        val localConfig: Path = homeLinkedPath.getLocalConfig()
        val configs: List<Path> = (if (fileManagementService.doesFileExist(localConfig)) {
            listOf(localConfig)
        } else {
            emptyList()
        }) + assistantConfigs
        val files: List<File> = configs.map { it.toFile() }
        return files.map {
            KubeConfigLocation(
                KubeConfig.loadKubeConfig(FileReader(it)),
                it.toPath()
            )
        }
    }

    fun saveContent(content: String) {
        getFolder()
        val file: File = homeLinkedPath.generateFileWithRandomName()
        fileManagementService.saveText(file, content)
    }

    fun saveFile(file: MultipartFile) {
        val name = file.originalFilename!!
        getFolder()
        val generatedFile = homeLinkedPath.generateFile(name)
        fileManagementService.saveBytes(generatedFile, file.bytes)
    }

    private fun getFolder(): Path {
        val folder: Path = homeLinkedPath.getAssistantFolder()
        fileManagementService.ensureFolderExists(folder)
        return folder
    }
}