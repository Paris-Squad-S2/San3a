package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val localDataStore: LocalDataStore
): ProfileRepository, BaseRepository() {
    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        localDataStore.setDarkTheme(isDarkTheme)
    }

    override fun isDarkThemeEnabled(): Flow<Boolean> {
        return localDataStore.isDarkThemeEnabled()
    }

    override fun getLatestSelectedAppLanguage(): Flow<String> {
        return localDataStore.getLatestSelectedAppLanguage()
    }

    override suspend fun updateAppLanguage(newLanguage: String): Boolean {
        return localDataStore.updateAppLanguage(newLanguage)
    }
}