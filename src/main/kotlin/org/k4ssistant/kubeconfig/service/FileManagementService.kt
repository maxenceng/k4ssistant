package org.k4ssistant.kubeconfig.service

import io.kubernetes.client.util.KubeConfig
import org.apache.commons.lang3.RandomStringUtils
import org.k4ssistant.kubeconfig.domain.model.KubeConfigLocation
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createDirectory
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.notExists

@Service
class FileManagementService {
    fun getAll(): List<KubeConfigLocation> {
        val localConfig = localConfigFile()
        val assistantConfigs = assistantFolder().configsInFolder()
        val configs = listOf(localConfig) + assistantConfigs
        val files = configs.filterNotNull()
            .filter { it.exists() }
            .map { it.toFile() }
        return files.map {
            KubeConfigLocation(
                KubeConfig.loadKubeConfig(FileReader(it)),
                it.toPath()
            )
        }
    }

    fun saveContent(content: String) {
        val file = assistantFolder().newContentFile()
        file.writeText(content)
    }

    fun saveFile(file: MultipartFile) {
        val name = file.originalFilename!!
        val os = FileOutputStream(assistantFolder().newFile(name))
        os.write(file.bytes)
        os.close()
    }

    private fun homeFolder(): Path = Paths.get(System.getProperty("user.home"))

    private fun localConfigFile(): Path? {
        val localConfig = Paths.get(homeFolder().toString(), ".kube", "config")
        return if (localConfig.notExists()) null
        else localConfig
    }

    private fun assistantFolder(): Path {
        val folder = Paths.get(homeFolder().toString(), "k4ssistant")
        if (folder.notExists()) {
            folder.createDirectory()
        }
        if (!folder.isDirectory()) {
            folder.deleteIfExists()
            folder.createDirectory()
        }
        return folder
    }

    private fun Path.configsInFolder(): List<Path> {
        return this.listDirectoryEntries().filter { !it.isDirectory() }
    }

    private fun Path.newContentFile(): File = Paths.get(
        this.toString(),
        RandomStringUtils.randomAlphanumeric(10)
    ).toFile()

    private fun Path.newFile(name: String): File = Paths.get(this.toString(), name).toFile()
}