package com.paris_2.san3a.data.source.remote.storage


abstract class StorageException(path: String, message: String) : Exception("$message : \t $path")

class WriteStorageException(path: String, message: String) : StorageException(path, message)
class ReadStorageException(path: String, message: String) : StorageException(path, message)
class DeleteStorageException(path: String, message: String) : StorageException(path, message)
class ImageNotFoundException(path: String, message: String) : StorageException(path, message)
class ImageStorageExceededException(path: String, message: String) : StorageException(path, message)
class ProjectNotFoundException(path: String, message: String) : StorageException(path, message)
class InvalidPathException(path: String, message: String = "Invalid path or path is empty") : StorageException(path, message)

class UnknownStorageException(path: String, message: String) : StorageException(path, message)