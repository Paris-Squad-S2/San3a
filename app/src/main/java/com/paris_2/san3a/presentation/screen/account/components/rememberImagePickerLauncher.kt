package com.paris_2.san3a.presentation.screen.account.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberSingleImagePickerLauncher(
    context: android.content.Context,
    onImageSelected: (Uri) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument()
) { uri: Uri? ->
    uri?.let {
        context.contentResolver.takePersistableUriPermission(
            it, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        onImageSelected(it)
    }
}

@Composable
fun rememberMultipleImagePickerLauncher(
    context: android.content.Context,
    onImagesSelected: (List<Uri>) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenMultipleDocuments()
) { uris: List<Uri>? ->
    uris?.let {
        it.forEach { uri ->
            context.contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
        onImagesSelected(it)
    }
}