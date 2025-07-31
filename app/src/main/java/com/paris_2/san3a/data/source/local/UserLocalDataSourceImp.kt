package com.paris_2.san3a.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.source.local.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull

class UserLocalDataSourceImp(
    private val dataStore: DataStore<Preferences>
) : UserLocalDataSource {
    override suspend fun setAccountType(accountType: AccountType) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(ACCOUNT_TYPE_KEY)] = accountType.name
        }
    }

    override suspend fun getAccountType(): AccountType? {
        val preferences = dataStore.data.firstOrNull()
        return preferences?.get(stringPreferencesKey(ACCOUNT_TYPE_KEY))?.let { accountTypeString ->
            AccountType.valueOf(accountTypeString)
        }
    }

    companion object {
        const val ACCOUNT_TYPE_KEY = "account_type"
    }
}