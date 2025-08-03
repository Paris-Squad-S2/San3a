package com.paris_2.san3a.domain.repository


interface AuthRepository {
    suspend fun sendMessage(
        phoneNumber: String,
        message: String,
    ): Boolean

    suspend fun savePhoneNumber(phoneNumber: String)

    suspend fun setLoggedIn(isLoggedIn: Boolean)

    suspend fun isLoggedIn(): Boolean
    suspend fun getPhoneNumber(): String
}