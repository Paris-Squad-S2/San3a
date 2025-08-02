package com.paris_2.san3a.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest

class LocalDataStoreImpl(
    private val dataStore: DataStore<Preferences>
): LocalDataStore {

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

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.setValue(KEY_USER_LOGGED_IN, isLoggedIn)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun isLoggedIn(): Boolean = dataStore.data.mapLatest {
        it[KEY_USER_LOGGED_IN] ?: false
    }.first()

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
        private val KEY_USER_LOGGED_IN = booleanPreferencesKey("user_logged_in")
    }
}