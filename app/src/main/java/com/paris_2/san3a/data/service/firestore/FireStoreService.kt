package com.paris_2.san3a.data.service.firestore

import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow

interface FireStoreService {
    suspend fun <T> getDoc(
        path: String,
        fromJson: (Map<String, Any>, String) -> T
    ): T?

    suspend fun updateDoc(path: String, data: Map<String, Any>)

    suspend fun deleteDoc(path: String)

    suspend fun batchWrite(operations: List<WriteOperation>)

    fun <T> streamDoc(
        path: String,
        fromJson: (Map<String, Any>) -> T
    ): Flow<T?>

    suspend fun addToCollection(
        path: String,
        data: Map<String, Any>
    ): String

    suspend fun <T : Any> setDoc(path: String, data: T): String

    suspend fun <T> getCollection(
        path: String,
        fromJson: (Map<String, Any>, String) -> T,
        queryBuilder: (Query) -> Query = { it },
        limit: Int? = null
    ): List<T>

    fun <T> streamCollection(
        path: String,
        fromJson: (Map<String, Any>, String) -> T,
        queryBuilder: (Query) -> Query = { it },
        limit: Int? = null
    ): Flow<List<T>>

    suspend fun getCountOfCollection(
        path: String,
        queryBuilder: (Query) -> Query = { it }
    ): Int

    suspend fun clearCollection(
        path: String
    )
}