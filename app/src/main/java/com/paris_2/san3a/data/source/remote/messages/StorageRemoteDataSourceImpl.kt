package com.paris_2.san3a.data.source.remote.messages

import android.net.Uri
import com.paris_2.san3a.data.service.storage.StorageServices

class StorageRemoteDataSourceImpl(
    private val storageServices: StorageServices,
) : StorageRemoteDataSource {
    override suspend fun saveImagesMessage(receiverId: Int, chatId: Int, uris: List<Uri>) {
        try {
            if (uris.isNotEmpty()) {
                val paths = uris.map { uri ->
                    "user$receiverId/chat$chatId/${uri.path?.substringAfterLast("/").orEmpty()}.jpg"
                }
                storageServices.saveImageFiles(
                    paths = paths,
                    uris = uris
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getImagesByPath(paths: List<String>): List<String> {
       return storageServices.getImagesByPath(paths)
    }
}