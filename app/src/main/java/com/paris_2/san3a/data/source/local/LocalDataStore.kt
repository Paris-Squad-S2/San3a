package com.paris_2.san3a.data.source.local

interface LocalDataStore {
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(completed: Boolean = true)
    suspend fun savePhoneNumber(phoneNumber: String)
    suspend fun setLoggedIn(isLoggedIn: Boolean)
    suspend fun isLoggedIn(): Boolean
}