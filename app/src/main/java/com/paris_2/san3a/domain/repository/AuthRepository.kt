package com.paris_2.san3a.domain.repository


interface AuthRepository {
    suspend fun sendMessage(
        phoneNumber: String,
        message: String,
    ): Boolean
}