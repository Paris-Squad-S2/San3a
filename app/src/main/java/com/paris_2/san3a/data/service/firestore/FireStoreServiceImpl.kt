package com.paris_2.san3a.data.service.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FireStoreServiceImpl(private val firestore: FirebaseFirestore) : FireStoreService {

    override suspend fun <T> getDoc(
        path: String,
        fromJson: (Map<String, Any>, String) -> T
    ): T? {
        return try {
            val snapshot = firestore.document(path).get().await()
            if (!snapshot.exists()) {
                throw DocumentNotFoundException(path)
            }
            val data = snapshot.data
            if (data == null) null else fromJson(data, snapshot.id)
        } catch (e: Exception) {
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                else -> throw GetDataException(path)
            }
        }
    }

    override suspend fun updateDoc(path: String, data: Map<String, Any>) {
        try {
            firestore.document(path).update(data).await()
        } catch (e: Exception) {
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                else -> throw UpdateDataException(path)
            }
        }
    }

    override suspend fun deleteDoc(path: String) {
        try {
            firestore.document(path).delete().await()
        } catch (e: Exception) {
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                else -> throw DeleteDataException(path)
            }
        }
    }

    override suspend fun batchWrite(operations: List<WriteOperation>) {
        try {
            val batch = firestore.batch()
            for (op in operations) {
                when (op.type) {
                    WriteOperationType.SET -> {
                        val docRef = firestore.document(op.path)
                        batch.set(docRef, op.data, SetOptions.merge())
                    }

                    WriteOperationType.UPDATE -> {
                        batch.update(firestore.document(op.path), op.data)
                    }

                    WriteOperationType.DELETE -> {
                        batch.delete(firestore.document(op.path))
                    }
                }
            }
            batch.commit().await()
        } catch (e: Exception) {
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, "batch_write")
                else -> throw BatchOperationException()
            }
        }
    }

    override fun <T> streamDoc(
        path: String,
        fromJson: (Map<String, Any>) -> T
    ): Flow<T?> = firestore.document(path)
        .snapshots()
        .map { snapshot ->
            if (!snapshot.exists()) {
                throw DocumentNotFoundException(path)
            }
            val data = snapshot.data
            if (data == null) null else fromJson(data)
        }
        .catch { e ->
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                else -> throw StreamDataException(path)
            }
        }

    override suspend fun addToCollection(path: String, data: Map<String, Any>): String {
        return try {
            val docRef = firestore.collection(path).add(data).await()
            docRef.id
        } catch (e: Exception) {
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                else -> throw SetDataException(path)
            }
        }
    }

    override suspend fun <T : Any> setDoc(documentPath: String, data: T): String {
        return try {
            firestore.document(documentPath).set(data).await()
            documentPath
        } catch (e: Exception) {
            Log.e("TAG", "setDoc: $e")
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, documentPath)
                else -> throw SetDataException(documentPath)
            }
        }
    }

    override suspend fun <T> getCollection(
        path: String,
        fromJson: (Map<String, Any>, String) -> T,
        queryBuilder: (Query) -> Query,
        limit: Int?
    ): List<T> {
        return try {
            var query: Query = firestore.collection(path)
            query = queryBuilder(query)
            if (limit != null) query = query.limit(limit.toLong())

            val snapshot = query.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.data?.let { fromJson(it, doc.id) }
            }
        } catch (e: Exception) {
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                else -> throw GetDataException(path)
            }
        }
    }

    override fun <T> streamCollection(
        path: String,
        fromJson: (Map<String, Any>, String) -> T,
        queryBuilder: (Query) -> Query,
        limit: Int?
    ): Flow<List<T>> {
        var query: Query = firestore.collection(path)
        query = queryBuilder(query)
        if (limit != null) query = query.limit(limit.toLong())

        return query.snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    doc.data?.let { data -> fromJson(data, doc.id) }
                }
            }
            .catch { e ->
                when (e) {
                    is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                    else -> throw StreamDataException(path)
                }
            }
    }

    private fun handleFirebaseException(e: FirebaseFirestoreException, path: String): Exception {
        return when (e.code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                PermissionDeniedException(path)

            FirebaseFirestoreException.Code.NOT_FOUND ->
                DocumentNotFoundException(path)

            FirebaseFirestoreException.Code.UNAVAILABLE ->
                NetworkException(path)

            else ->
                FireStoreOperationException(path, e.message)
        }
    }

    override suspend fun getCountOfCollection(
        path: String,
        queryBuilder: (Query) -> Query
    ): Int {
        return try {
            var query: Query = firestore.collection(path)
            query = queryBuilder(query)

            val snapshot = query.get().await()
            snapshot.size()
        } catch (e: Exception) {
            when (e) {
                is FirebaseFirestoreException -> throw handleFirebaseException(e, path)
                else -> throw GetDataException(path)
            }
        }
    }
}