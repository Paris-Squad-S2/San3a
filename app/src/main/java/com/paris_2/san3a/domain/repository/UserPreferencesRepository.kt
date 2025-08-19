package com.paris_2.san3a.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun setDarkTheme(isDarkTheme: Boolean)
    suspend fun isDarkThemeEnabled(): Flow<Boolean>
    fun getLatestSelectedAppLanguage(): Flow<String>
    suspend fun updateAppLanguage(newLanguage: String): Boolean
    suspend fun isOnboardingCompleted(): Boolean
    suspend fun setOnboardingCompleted()
}