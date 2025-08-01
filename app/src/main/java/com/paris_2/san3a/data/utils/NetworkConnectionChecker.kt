package com.paris_2.san3a.data.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkConnectionChecker(private val context: Context) {
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    init {
        startChecker()
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isConnected.value = true
        }

        override fun onLost(network: Network) {
            _isConnected.value = false
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun startChecker() {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        _isConnected.value = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}