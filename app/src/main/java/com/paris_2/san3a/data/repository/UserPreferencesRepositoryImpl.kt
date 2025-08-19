package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.source.local.userPreferences.UserPreferencesLocalDataStore
import com.paris_2.san3a.domain.exceptions.FailException
import com.paris_2.san3a.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class UserPreferencesRepositoryImpl(
    private val userPreferencesLocalDataStore: UserPreferencesLocalDataStore
) : UserPreferencesRepository, BaseRepository() {

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        safeCall(FailException("Failed to set dark theme")) {
            userPreferencesLocalDataStore.setDarkTheme(isDarkTheme)
        }
    }

    override suspend fun isDarkThemeEnabled(): Flow<Boolean> {
        return safeCall(FailException("Failed to get dark theme status")) {
            userPreferencesLocalDataStore.isDarkThemeEnabled()
        }
    }

    override fun getLatestSelectedAppLanguage(): Flow<String> =
        userPreferencesLocalDataStore.getLatestSelectedAppLanguage()
            .catch { throw FailException("failed to get selected language") }

    override suspend fun updateAppLanguage(newLanguage: String): Boolean {
        return safeCall(FailException("Failed to update app language")) {
            userPreferencesLocalDataStore.updateAppLanguage(newLanguage)
        }
    }

    override suspend fun getVersionName(): String {
        return safeCall(FailException("Failed to get version name")) {
            userPreferencesLocalDataStore.getVersionName()
        }
    }

    override suspend fun setOnboardingCompleted() {
        safeCall(FailException("Failed to set onboarding completed")) {
            userPreferencesLocalDataStore.setOnboardingCompleted()
        }
    }

    override suspend fun isOnboardingCompleted(): Boolean {
        return safeCall(FailException("Failed to check if onboarding is completed")) {
            userPreferencesLocalDataStore.isOnboardingCompleted()
        }
    }
}