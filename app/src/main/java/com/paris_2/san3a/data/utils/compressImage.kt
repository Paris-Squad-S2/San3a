package com.paris_2.san3a.data.utils

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream // TODO: replace java with kotlin

fun Uri.compressImage(context: Context, quality: Int = 60): ByteArray {
    val bitmap = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        android.graphics.ImageDecoder.decodeBitmap(
            android.graphics.ImageDecoder.createSource(context.contentResolver, this)
        )
    } else {
        @Suppress("DEPRECATION")
        android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, this)
    }
    return ByteArrayOutputStream().use { stream ->
        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, quality, stream)
        stream.toByteArray()
    }
}