package com.paris_2.san3a.data.repository

import android.util.Log
import com.paris_2.san3a.domain.San3aException

open class BaseRepository() {

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