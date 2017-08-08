package net.blerg.hilbre

import java.io.File
import java.nio.file.StandardWatchEventKinds
import java.nio.file.FileSystems.getFileSystem
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.WatchService
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.FileSystems




class DirectoryWatcher(val path:String) {

    fun scan(action: (FileAction, String) -> Unit) {

        val path = Paths.get(path)
        val watchService = FileSystems.getDefault().newWatchService()
        val key = path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE)

        while(true) {
            val key = watchService.take()

            for (event in key.pollEvents()) {
                val fileAction = when(event.kind()) {
                    ENTRY_DELETE -> FileAction.DELETE
                    ENTRY_MODIFY -> FileAction.MODIFY
                    ENTRY_CREATE -> FileAction.CREATE
                    else -> FileAction.OTHER
                }
                action.invoke(fileAction, event.context().toString())
            }
            key.reset()
        }

    }
}