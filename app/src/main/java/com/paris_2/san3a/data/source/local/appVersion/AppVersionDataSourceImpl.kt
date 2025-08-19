package com.paris_2.san3a.data.source.local.appVersion

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class AppVersionDataSourceImpl(private val context: Context) : AppVersionDataSource {
    override fun getVersionName(): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager
                    .getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
                    .versionName ?: "1.0.0."
            } else {
                context.packageManager
                    .getPackageInfo(context.packageName, 0)
                    .versionName ?: "1.0.0"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw e
        }
    }
}