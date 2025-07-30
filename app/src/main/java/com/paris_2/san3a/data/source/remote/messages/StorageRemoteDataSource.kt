package com.paris_2.san3a.data.source.remote.messages

import android.net.Uri

interface StorageRemoteDataSource {
    suspend fun saveImagesMessage(receiverId:Int, chatId:Int, uris: List<Uri>)
    suspend fun getImagesByPath(path: List<String>):List<String>
}