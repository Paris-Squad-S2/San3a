package com.paris_2.san3a.presentation.utill

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getVersionName(): String {
    val context = LocalContext.current
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.packageManager
            .getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            .versionName ?: "1.0.0."
    } else {
        context.packageManager
            .getPackageInfo(context.packageName, 0)
            .versionName ?: "1.0.0"
    }
}