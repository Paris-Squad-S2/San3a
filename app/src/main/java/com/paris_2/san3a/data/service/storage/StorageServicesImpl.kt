package com.paris_2.san3a.data.service.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.tasks.await

class StorageServicesImpl(
    private val storageService: FirebaseStorage,
) : StorageServices {
    override suspend fun saveImageFiles(paths: List<String>, uris: List<Uri>){
        return try {
            val storageRef = storageService.reference
            for ((index, uri) in uris.withIndex()) {
                val path = paths[index]
                val imageRef = storageRef.child(path)
                imageRef.putFile(uri)
                    .addOnFailureListener {
                        throw it
                    }
            }
        } catch (e: StorageException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getImagesByPath(paths: List<String>):List<String> {
       return paths.map { path ->
            storageService.reference.child(path).downloadUrl.await().toString()
        }
    }
}