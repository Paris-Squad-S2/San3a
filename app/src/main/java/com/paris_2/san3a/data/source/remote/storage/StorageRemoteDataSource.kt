package com.paris_2.san3a.data.source.remote.storage

import android.net.Uri

interface StorageRemoteDataSource {
    suspend fun saveImages(paths: List<String>, uris: List<Uri>)
    suspend fun getImagesByPaths(paths: List<String>, uris: List<Uri>): List<String>
    suspend fun deleteImages(paths: List<String>)
}