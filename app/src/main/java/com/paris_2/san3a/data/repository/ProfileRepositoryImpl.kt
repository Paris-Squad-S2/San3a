package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.domain.ProfileException
import com.paris_2.san3a.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class ProfileRepositoryImpl(
    private val localDataStore: LocalDataStore
): ProfileRepository, BaseRepository() {
    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        safeCall(ProfileException()) {
            localDataStore.setDarkTheme(isDarkTheme)
        }
    }

    override suspend fun isDarkThemeEnabled(): Flow<Boolean> {
        return safeCall(ProfileException()) {
            localDataStore.isDarkThemeEnabled()
        }
    }

    override fun getLatestSelectedAppLanguage(): Flow<String> {
        return localDataStore.getLatestSelectedAppLanguage()
            .catch { ProfileException() }
    }

    override suspend fun updateAppLanguage(newLanguage: String): Boolean {
        return safeCall(ProfileException()){
            localDataStore.updateAppLanguage(newLanguage)
        }
    }

    override suspend fun getVersionName(): String {
        return safeCall(ProfileException()) {
            localDataStore.getVersionName()
        }
    }
}