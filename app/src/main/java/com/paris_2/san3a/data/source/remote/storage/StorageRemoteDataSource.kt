package com.paris_2.san3a.data.source.remote.storage

import com.paris_2.san3a.data.source.remote.storage.dto.ImageDto

interface StorageRemoteDataSource {
    suspend fun saveImages(images: List<ImageDto>): List<String>
    suspend fun getImagesByPaths(images: List<ImageDto>): List<String>
    suspend fun deleteImages(images: List<ImageDto>)
}