package com.paris_2.san3a.data.source.remote.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.tasks.await

class FirebaseStorageDataSource(
    private val fireStorage: FirebaseStorage,
) : StorageRemoteDataSource {
    override suspend fun saveImages(paths: List<String>, uris: List<Uri>) {
        try {
            if (uris.isNotEmpty()) {
                val storageRef = fireStorage.reference
                for ((index, uri) in uris.withIndex()) {
                    val path = paths[index]
                    val imageRef = storageRef.child(path)
                    imageRef.putFile(uri).await()
                }
            } else {
                throw InvalidPathException(paths.toOneString())
            }
        } catch (e: StorageException) {
            throw FireStorageException(paths.toOneString(), e.message.orEmpty())
        } catch (e: com.paris_2.san3a.data.source.remote.storage.StorageException) {
            throw e
        } catch (e: Exception) {
            throw WriteStorageException(paths.toOneString(), e.message.orEmpty())
        }
    }

    override suspend fun getImagesByPaths(paths: List<String>): List<String> {
        return try {
            if (paths.isNotEmpty()) {
                paths.map { path ->
                    val url = fireStorage.reference.child(path).downloadUrl
                    url.await().toString()
                }
            } else {
                emptyList()
            }
        } catch (e: StorageException) {
            throw FireStorageException(paths.toOneString(), e.message.orEmpty())
        } catch (e: com.paris_2.san3a.data.source.remote.storage.StorageException) {
            throw e
        } catch (e: Exception) {
            throw ReadStorageException(paths.toOneString(), e.message.orEmpty())
        }
    }

    private fun List<String>.toOneString(): String = this.joinToString { it }
}