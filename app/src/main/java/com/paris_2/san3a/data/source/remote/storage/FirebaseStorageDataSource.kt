package com.paris_2.san3a.data.source.remote.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.paris_2.san3a.data.service.firestore.PermissionDeniedException
import com.paris_2.san3a.data.source.remote.storage.dto.ImageDto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FirebaseStorageDataSource(
    private val fireStorage: FirebaseStorage,
) : StorageRemoteDataSource {
    override suspend fun saveImages(images: List<ImageDto>) = coroutineScope {
        try {
            val storageRef = fireStorage.reference
            images.ifEmpty { throw InvalidPathException(images.map { it.path }.toOneString()) }
                .filter { image -> isFirebaseStorageUri(image.uri).not() }
                .map { image ->
                    val imageRef = storageRef.child(image.path)
                    async {
                        imageRef.putFile(image.uri).await()
                        imageRef.downloadUrl.await().toString()
                    }
                }.awaitAll()
        } catch (e: Exception) {
            throw handleStorageException(images.map { it.path }, e, StorageOperationType.SAVE)
        }
    }

    private fun isFirebaseStorageUri(uri: Uri): Boolean =
        runCatching { fireStorage.getReferenceFromUrl(uri.toString()) }.isSuccess

    override suspend fun getImagesByPaths(images: List<ImageDto>): List<String> = coroutineScope {
        try {
            images.ifEmpty { throw InvalidPathException(images.map { it.path }.toOneString()) }
                .map { image ->
                    async {
                        fireStorage.reference.child(image.path).downloadUrl
                        .await().toString()
                    }
                }.awaitAll()
        } catch (e: Exception) {
            reSaveImages(images, e)
        }
    }

    override suspend fun deleteImages(images: List<ImageDto>) {
        try {
            images.ifEmpty { throw InvalidPathException(images.map { it.path }.toOneString()) }
                .forEach { image ->
                    val imageRef = fireStorage.reference.child(image.path)
                    imageRef.delete().await()
                }
        } catch (e: Exception) {
            throw handleStorageException(images.map { it.path }, e, StorageOperationType.DELETE)
        }
    }

    private suspend fun reSaveImages(
        images: List<ImageDto>,
        e: Exception,
    ): List<String> {
        val errorCode = if (e is StorageException) e.errorCode else null
        if (errorCode == StorageException.ERROR_OBJECT_NOT_FOUND || errorCode == StorageException.ERROR_BUCKET_NOT_FOUND) {
            return saveImages(images)
        } else {
            throw handleStorageException(images.map { it.path }, e, StorageOperationType.GET)
        }
    }

    private fun handleStorageException(
        paths: List<String>,
        e: Exception,
        operationType: StorageOperationType,
    ): Exception {
        val errorCode = if (e is StorageException) e.errorCode else null
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