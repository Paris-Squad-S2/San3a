package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
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

    override suspend fun verifyOtp(verificationId: String, code: String) {
        return safeCall(RegisterException()){
            remoteDataSource.verifyOtp(verificationId, code)
        }
    }

    private suspend fun <T> safeCall(exception: San3aException, call: suspend () -> T): T {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return try {
            call()
        } catch (e: San3aException) {
            throw e
        } catch (_: Exception) {
            throw exception
        }
    }
}