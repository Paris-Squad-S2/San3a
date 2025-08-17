package com.paris_2.san3a.data.source.remote.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.paris_2.san3a.data.service.firestore.PermissionDeniedException
import kotlinx.coroutines.tasks.await

class FirebaseStorageDataSource(
    private val fireStorage: FirebaseStorage,
) : StorageRemoteDataSource {
    override suspend fun saveImages(paths: List<String>, uris: List<Uri>) {
        try {
            uris.ifEmpty { throw InvalidPathException(paths.toOneString()) }
                .filter { uri -> isFirebaseStorageUri(uri).not() }
                .forEachIndexed { index, uri ->
                    val storageRef = fireStorage.reference
                    val imageRef = storageRef.child(paths[index])
                    imageRef.putFile(uri).await()
                }
        } catch (e: Exception) {
            throw handleStorageException(paths, e, StorageOperationType.SAVE)
        }
    }

    private fun isFirebaseStorageUri(uri: Uri): Boolean {
        return try {
            fireStorage.getReferenceFromUrl(uri.toString())
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getImagesByPaths(paths: List<String>, uris: List<Uri>): List<String> {
        return try {
            paths.ifEmpty { throw InvalidPathException(paths.toOneString()) }
                .map { path ->
                    val url = fireStorage.reference.child(path).downloadUrl
                    url.await().toString()
                }
        } catch (e: Exception) {
            reSaveImages(paths, uris, e)
        }
    }

    override suspend fun deleteImages(paths: List<String>) {
        try {
            paths.ifEmpty { throw InvalidPathException(paths.toOneString()) }
                .forEach { path ->
                    val imageRef = fireStorage.reference.child(path)
                    imageRef.delete().await()
                }
        }catch (e: Exception){
            throw handleStorageException(paths, e, StorageOperationType.DELETE)
        }
    }

    private suspend fun reSaveImages(
        paths: List<String>,
        uris: List<Uri>,
        e: Exception,
    ):List<String> {
        val errorCode = (e as? StorageException)?.errorCode
        if (errorCode == StorageException.ERROR_OBJECT_NOT_FOUND || errorCode == StorageException.ERROR_BUCKET_NOT_FOUND) {
            saveImages(paths, uris)
            return getImagesByPaths(paths, uris)
        }else{
            throw handleStorageException(paths, e, StorageOperationType.GET)
        }
    }

    private fun handleStorageException(
        paths: List<String>,
        e: Exception,
        operationType: StorageOperationType,
    ): Exception {
        val errorCode = (e as? StorageException)?.errorCode
        when (errorCode) {
            StorageException.ERROR_OBJECT_NOT_FOUND -> {
                return ImageNotFoundException(paths.toOneString(), e.message.orEmpty())
            }

            StorageException.ERROR_BUCKET_NOT_FOUND -> {
                return ImageNotFoundException(paths.toOneString(), e.message.orEmpty())
            }

            StorageException.ERROR_CANCELED -> {
                return when (operationType) {
                    StorageOperationType.SAVE -> WriteStorageException(
                        paths.toOneString(),
                        e.message.orEmpty()
                    )

                    StorageOperationType.GET -> ReadStorageException(
                        paths.toOneString(),
                        e.message.orEmpty()
                    )

                    StorageOperationType.DELETE -> DeleteStorageException(
                        paths.toOneString(),
                        e.message.orEmpty()
                    )
                }
            }

            StorageException.ERROR_INVALID_CHECKSUM -> {
                return when (operationType) {
                    StorageOperationType.SAVE -> WriteStorageException(
                        paths.toOneString(),
                        e.message.orEmpty()
                    )

                    StorageOperationType.GET -> ReadStorageException(
                        paths.toOneString(),
                        e.message.orEmpty()
                    )

                    StorageOperationType.DELETE -> DeleteStorageException(
                        paths.toOneString(),
                        e.message.orEmpty()
                    )
                }
            }

            StorageException.ERROR_NOT_AUTHENTICATED -> {
                return PermissionDeniedException(paths.toOneString())
            }

            StorageException.ERROR_NOT_AUTHORIZED -> {
                return PermissionDeniedException(paths.toOneString())
            }

            StorageException.ERROR_PROJECT_NOT_FOUND -> {
                return ProjectNotFoundException(paths.toOneString(), e.message.orEmpty())
            }

            StorageException.ERROR_QUOTA_EXCEEDED -> {
                return ImageStorageExceededException(paths.toOneString(), e.message.orEmpty())
            }

            StorageException.ERROR_RETRY_LIMIT_EXCEEDED -> {
                return ImageStorageExceededException(paths.toOneString(), e.message.orEmpty())
            }

            StorageException.ERROR_UNKNOWN -> {
                return UnknownStorageException(paths.toOneString(), e.message.orEmpty())
            }
        }

        return when (operationType) {
            StorageOperationType.SAVE -> {
                WriteStorageException(paths.toOneString(), e.message.orEmpty())
            }

            StorageOperationType.GET -> {
                ReadStorageException(paths.toOneString(), e.message.orEmpty())
            }

            StorageOperationType.DELETE -> DeleteStorageException(
                paths.toOneString(),
                e.message.orEmpty()
            )
        }
    }

    enum class StorageOperationType {
        SAVE,
        GET,
        DELETE
    }

    private fun List<String>.toOneString(): String = this.joinToString { it }
}