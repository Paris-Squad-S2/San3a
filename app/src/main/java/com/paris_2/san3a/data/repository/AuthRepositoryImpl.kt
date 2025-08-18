package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.service.auth.WhatsAppMessage
import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.FailException
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataStoreImpl: LocalDataStore,
) : AuthRepository, BaseRepository() {

    override suspend fun sendMessage(
        phoneNumber: String,
        message: String,
    ): Boolean {
        validateNetworkConnection()
        return safeCall(FailException("register error")) {
            val body = WhatsAppMessage(phoneNumber = phoneNumber, message = message)
            remoteDataSource.sendMessage(body).success ?: false
        }
    }

    override suspend fun savePhoneNumber(phoneNumber: String) {
        safeCall(FailException("save phone number error")) {
            localDataStoreImpl.savePhoneNumber(phoneNumber)
        }
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        return safeCall(FailException("set logged in error")) {
            localDataStoreImpl.setLoggedIn(isLoggedIn)
        }
    }

    override suspend fun getPhoneNumber(): String {
        return safeCall(FailException("get phone number error")) {
            localDataStoreImpl.getPhoneNumber()
        }
    }
}