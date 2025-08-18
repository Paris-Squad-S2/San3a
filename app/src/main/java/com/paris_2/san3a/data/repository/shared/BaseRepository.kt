package com.paris_2.san3a.data.repository.shared

import android.util.Log
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.San3aException
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
}