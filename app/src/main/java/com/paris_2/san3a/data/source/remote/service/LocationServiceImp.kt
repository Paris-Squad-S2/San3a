package com.paris_2.san3a.data.source.remote.service

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.paris_2.san3a.data.mapper.toLocationDto
import com.paris_2.san3a.data.repository.service.LocationService
import com.paris_2.san3a.data.source.remote.dto.LocationDto
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationServiceImp(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,
) : LocationService {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationDto? = suspendCoroutine { continuation ->
        if (!isHasPermissionLocation()) {
            continuation.resume(null)
            return@suspendCoroutine
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                continuation.resume(it.toLocationDto())
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }

    private fun isHasPermissionLocation(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }
}