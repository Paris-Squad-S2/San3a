package com.paris_2.san3a.data.repository

import android.util.Log
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.RegisterException
import com.paris_2.san3a.domain.San3aException
import com.paris_2.san3a.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val remoteDataSource: AuthRemoteDataSource
): AuthRepository {
    override suspend fun sendOtp(phoneNumber: String): String {
        return safeCall(RegisterException()){
            val verificationId = remoteDataSource.sendOtp(phoneNumber)
            verificationId
        }
    }

    override suspend fun verifyOtp(verificationId: String, code: String): String {
        return safeCall(RegisterException()){
            remoteDataSource.verifyOtp(verificationId, code).user?.uid ?: throw RegisterException()
        }
    }

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
}