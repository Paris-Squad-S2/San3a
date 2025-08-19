package com.paris_2.san3a.data.source.local.userPreferences

import kotlinx.coroutines.flow.Flow

interface UserPreferencesLocalDataStore {
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted(completed: Boolean = true)
    suspend fun savePhoneNumber(phoneNumber: String)
    suspend fun getPhoneNumber(): String
    suspend fun setDarkTheme(isDarkTheme: Boolean)
    fun isDarkThemeEnabled(): Flow<Boolean>
    fun getLatestSelectedAppLanguage(): Flow<String>
    suspend fun updateAppLanguage(newLanguage: String): Boolean
    suspend fun getVersionName(): String
}