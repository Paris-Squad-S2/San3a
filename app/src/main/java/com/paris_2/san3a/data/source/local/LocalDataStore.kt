package com.paris_2.san3a.data.source.local

import kotlinx.coroutines.flow.Flow

interface LocalDataStore {
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(completed: Boolean = true)
    suspend fun savePhoneNumber(phoneNumber: String)
    suspend fun setLoggedIn(isLoggedIn: Boolean)
    suspend fun isLoggedIn(): Boolean
    suspend fun getPhoneNumber(): String
    suspend fun setDarkTheme(isDarkTheme: Boolean)
    fun isDarkThemeEnabled(): Flow<Boolean>
    fun getLatestSelectedAppLanguage(): Flow<String>
    suspend fun updateAppLanguage(newLanguage: String): Boolean
}