package com.paris_2.san3a.data.repository.shared

import android.util.Log
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.exceptions.NoInternetConnectionException
import com.paris_2.san3a.domain.exceptions.San3aException
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

open class BaseRepository {

    private val networkConnectionChecker: NetworkConnectionChecker by inject(NetworkConnectionChecker::class.java)

    fun validateNetworkConnection(){
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
    }

    suspend fun <T> safeCall(exception: San3aException, call: suspend () -> T): T {
        return try {
            call()
        } catch (e: San3aException) {
            Log.d("BaseRepository", "safeCall: ${e.message}")
            throw e
        } catch (a: Exception) {
            Log.e("BaseRepository", "safeCall: ${a.message}")
            throw exception
        }
    }

    suspend fun <T> safeNetworkCall(
        exception: San3aException,
        call: suspend () -> T,
        maxAttempts: Int = 1
    ): T {
        validateNetworkConnection()
        var attempt = 0
        while (attempt < maxAttempts) {
            try {
                return call()
            } catch (e: San3aException) {
                Log.d("BaseRepository", "safeNetworkCall attempt $attempt: ${e.message}")
                throw e
            } catch (a: Exception) {
                Log.e("BaseRepository", "safeNetworkCall attempt $attempt: ${a.message}")
                attempt++
                if (attempt >= maxAttempts) {
                    throw exception
                }
            }
        }
        throw exception
    }
}