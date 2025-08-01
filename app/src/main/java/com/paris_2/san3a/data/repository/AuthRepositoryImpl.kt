package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.service.auth.WhatsAppMessage
import com.paris_2.san3a.data.source.AppPreferences
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.PhoneNumberCheckException
import com.paris_2.san3a.domain.RegisterException
import com.paris_2.san3a.domain.SavePhoneNumberException
import com.paris_2.san3a.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val remoteDataSource: AuthRemoteDataSource,
    private val appPreferences: AppPreferences
): AuthRepository, BaseRepository() {

    override suspend fun sendMessage(
        phoneNumber: String,
        message: String,
    ): Boolean {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
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
        safeCall(SavePhoneNumberException()) {
            appPreferences.savePhoneNumber(phoneNumber)
        }
    }

    override suspend fun isPhoneNumberSaved(): Boolean {
        return safeCall(PhoneNumberCheckException()) {
            appPreferences.isPhoneNumberSaved()
        }
    }
}