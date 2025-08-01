package com.paris_2.san3a.data.repository

import android.util.Log
import com.paris_2.san3a.data.service.auth.WhatsAppMessage
import com.paris_2.san3a.data.source.AppPreferences
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.RegisterException
import com.paris_2.san3a.domain.San3aException
import com.paris_2.san3a.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val remoteDataSource: AuthRemoteDataSource,
    private val appPreferences: AppPreferences
): AuthRepository {


    private suspend fun <T> safeCall(exception: San3aException, call: suspend () -> T): T {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return try {
            call()
        } catch (e: San3aException) {
            Log.d("AuthRepositoryImpl", "safeCall: ${e.message}")
            throw e
        } catch (a: Exception) {
            Log.d("AuthRepositoryImpl2", "safeCall: ${a.message}")

            throw exception
        }
    }

    override suspend fun sendMessage(
        phoneNumber: String,
        message: String,
    ): Boolean {
        return safeCall(RegisterException()) {
            val body = WhatsAppMessage(
                phoneNumber = phoneNumber,
                message = message
            )
            remoteDataSource.sendMessage(
                body
            ).success ?: false
        }
    }

    override suspend fun savePhoneNumber(phoneNumber: String) {
        appPreferences.savePhoneNumber(phoneNumber)
    }

    override suspend fun isPhoneNumberSaved(): Boolean {
        return appPreferences.isPhoneNumberSaved()
    }
}