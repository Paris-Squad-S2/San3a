package com.paris_2.san3a.data.service.firestore

abstract class FireStoreServiceException(message: String? = null) : Exception(message)

class DocumentNotFoundException(path: String) : FireStoreServiceException("Document not found at path: $path")
class GetDataException(path: String) : FireStoreServiceException("Failed to get data at path: $path")
class UpdateDataException(path: String) : FireStoreServiceException("Failed to update data at path: $path")
class DeleteDataException(path: String) : FireStoreServiceException("Failed to delete data at path: $path")
class BatchOperationException(message: String) : FireStoreServiceException("Batch operation failed: $message")
class StreamDataException(path: String) : FireStoreServiceException("Failed to stream data at path: $path")
class SetDataException(path: String) : FireStoreServiceException("Failed to set data at path: $path")
class PermissionDeniedException(path: String) : FireStoreServiceException("Permission denied for path: $path")
class NetworkException(path: String) : FireStoreServiceException("Network error at path: $path")
class FireStoreOperationException(path: String, exceptionMessage: String?) : FireStoreServiceException("Firebase operation failed at path: $path. Details: $exceptionMessage")
class ClearCollectionException(path: String) : FireStoreServiceException("Failed to clear collection at path: $path")