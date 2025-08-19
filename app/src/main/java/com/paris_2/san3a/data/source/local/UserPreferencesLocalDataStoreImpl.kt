package com.paris_2.san3a.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class UserPreferencesLocalDataStoreImpl(
    private val dataStore: DataStore<Preferences>,
    private val appVersionDataSource: AppVersionDataSource
): UserPreferencesLocalDataStore {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun isOnboardingCompleted(): Boolean = dataStore.data.mapLatest {
        it[KEY_ONBOARDING_COMPLETED] ?: false
    }.first()

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.setValue(KEY_ONBOARDING_COMPLETED, completed)
    }

    override suspend fun savePhoneNumber(phoneNumber: String) {
        dataStore.setValue(PHONE_NUMBER, phoneNumber)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getPhoneNumber(): String {
        return dataStore.data.mapLatest {
            it[PHONE_NUMBER] ?: ""
        }.first()
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        dataStore.setValue(IS_DARK_THEME, isDarkTheme)
    }

    override fun isDarkThemeEnabled(): Flow<Boolean> {
        return dataStore.data.map {
            it[IS_DARK_THEME] ?: false
        }
    }

    override suspend fun updateAppLanguage(newLanguage: String): Boolean {
        dataStore.setValue(LOCAL_LANGUAGE, newLanguage)
        return true
    }

    override suspend fun getVersionName(): String {
        saveVersionName()
        return dataStore.data.map {
            it[APP_VERSION_NAME] ?: DEFAULT_VERSION_NAME
        }.first()
    }

    private suspend fun saveVersionName() {
        val versionName = appVersionDataSource.getVersionName()
        dataStore.setValue(APP_VERSION_NAME,versionName)
    }

    override fun getLatestSelectedAppLanguage(): Flow<String> =
        dataStore.data.map {
            it[LOCAL_LANGUAGE] ?: ENGLISH
        }


    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T,
    ) {
        this.edit { preferences ->
            preferences[key] = value
        }
    }

    companion object {
        private val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        private val PHONE_NUMBER = stringPreferencesKey("phone_number")
        private val IS_DARK_THEME = booleanPreferencesKey("Is_Dark_Theme")
        private val LOCAL_LANGUAGE = stringPreferencesKey("LOCAL_LANGUAGE")
        private val APP_VERSION_NAME = stringPreferencesKey("APP_VERSION_NAME")
        const val ENGLISH = "en"
        const val DEFAULT_VERSION_NAME = "1.0.0"
    }
}