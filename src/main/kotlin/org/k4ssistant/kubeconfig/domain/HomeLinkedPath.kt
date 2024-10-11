package org.k4ssistant.kubeconfig.domain

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
class HomeLinkedPath(
    @Value("\${project.name}")
    private val projectName: String
) {
    companion object {
        private val HOME_PATH = Paths.get(System.getProperty("user.home")).toString()
        private val DEFAULT_CONFIG_PATH = listOf(".kube", "config")
    }

    private fun get(vararg paths: String): Path = Paths.get(
        HOME_PATH,
        *paths
    )

    fun getLocalConfig(): Path = get(*DEFAULT_CONFIG_PATH.toTypedArray())

    fun getAssistantFolder(): Path = get(projectName)

    fun generateFileWithRandomName(): File = Paths.get(
        getAssistantFolder().toString(),
        RandomStringUtils.randomAlphanumeric(10)
    ).toFile()

    fun generateFile(name: String): File = Paths.get(getAssistantFolder().toString(), name).toFile()
}