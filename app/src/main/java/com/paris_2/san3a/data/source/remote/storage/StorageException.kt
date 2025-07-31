package com.paris_2.san3a.data.source.remote.storage


abstract class StorageException(path: String, message: String): Exception("$message : \t $path")

class FireStorageException(path: String, message: String) : StorageException(path,message)
class WriteStorageException(path: String, message: String) : StorageException(path,message)
class InvalidPathException(path: String, message: String="Invalid path") : StorageException(path,message)
class ReadStorageException(path: String, message: String) : StorageException(path,message)
class EmptyMessageException(message: String): StorageException("in general","No Message Found : $message")