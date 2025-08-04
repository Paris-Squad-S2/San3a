package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.domain.ProfileException
import com.paris_2.san3a.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

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

    override suspend fun getLatestSelectedAppLanguage(): Flow<String> {
        return safeCall(ProfileException()) {
            localDataStore.getLatestSelectedAppLanguage()
        }
    }

    override suspend fun updateAppLanguage(newLanguage: String): Boolean {
        return safeCall(ProfileException()){
            localDataStore.updateAppLanguage(newLanguage)
        }
    }
}