package org.k4ssistant.kubeconfig.infrastruture.primary

import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.notExists

@Service
class FileManagementService() {

    fun doesFileExist(file: Path): Boolean {
        return !file.exists() || file.isDirectory()
    }

    fun ensureFolderExists(folder: Path) {
        try {
            if (folder.notExists()) {
                folder.createDirectory()
            }
            if (!folder.isDirectory()) {
                folder.deleteIfExists()
                folder.createDirectory()
            }
        } catch (e: IOException) {
            throw FolderCreationException(this.toString())
        }
    }

    fun filesInFolder(folder: Path): List<Path> {
        return folder.listDirectoryEntries()
            .filter { !it.isDirectory() }
    }

    fun saveText(file: File, content: String) {
        file.writeText(content)
    }

    fun saveBytes(file: File, bytes: ByteArray) {
        file.writeBytes(bytes)
    }
}