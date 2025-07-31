package com.paris_2.san3a.data.service.firestore

sealed class WriteOperation(val type: WriteOperationType, open val path: String, open val data: Map<String, Any> = emptyMap(), open val merge: Boolean = false)

data class SetOperation(
    override val path: String,
    override val data: Map<String, Any>,
    override val merge: Boolean = false
) : WriteOperation(WriteOperationType.SET, path, data, merge)

data class UpdateOperation(
    override val path: String,
    override val data: Map<String, Any>
) : WriteOperation(WriteOperationType.UPDATE, path, data)

data class DeleteOperation(
    override val path: String
) : WriteOperation(WriteOperationType.DELETE, path)

enum class WriteOperationType {
    SET, UPDATE, DELETE
}