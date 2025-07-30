package com.paris_2.san3a.data.service.storage

import android.net.Uri

interface StorageServices {
    suspend fun saveImageFiles(paths: List<String>, uris: List<Uri>)
    suspend fun getImagesByPath(paths:List<String>): List<String>
}