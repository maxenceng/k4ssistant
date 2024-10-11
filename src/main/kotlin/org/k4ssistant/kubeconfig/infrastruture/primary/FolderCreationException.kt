package org.k4ssistant.kubeconfig.infrastruture.primary

class FolderCreationException(folder: String) : RuntimeException("Could not create folder $folder")