package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toCities
import com.paris_2.san3a.data.mapper.toCity
import com.paris_2.san3a.data.mapper.toGovernorate
import com.paris_2.san3a.data.mapper.toStates
import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.source.local.userPreferences.UserPreferencesLocalDataStore
import com.paris_2.san3a.data.source.local.location.LocationLocalDataSource
import com.paris_2.san3a.domain.exceptions.FailException
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
import com.paris_2.san3a.domain.repository.LocationRepository
import kotlinx.coroutines.flow.first

class LocationRepositoryImp(
    private val locationLocalDataSource: LocationLocalDataSource,
    private val userPreferencesLocalDataStore: UserPreferencesLocalDataStore
) : LocationRepository, BaseRepository() {

    override suspend fun getGovernorates(): List<Governorate> {
        return safeCall(FailException("getGovernorates")) {
            val language = userPreferencesLocalDataStore.getLatestSelectedAppLanguage().first()
            locationLocalDataSource.getGovernorates().toStates(language = language)
        }
    }

    override suspend fun getCitiesInGovernment(governorateId: Int): List<City> {
        return safeCall(FailException("getCitiesInGovernment")) {
            val language = userPreferencesLocalDataStore.getLatestSelectedAppLanguage().first()
            locationLocalDataSource.getCities(governorateId).toCities(language = language)
        }
    }

    override suspend fun getGovernorateById(governorateId: Int): Governorate? {
        return safeCall(FailException("getGovernorateById")) {
            val language = userPreferencesLocalDataStore.getLatestSelectedAppLanguage().first()
            locationLocalDataSource.getGovernorateById(governorateId)?.toGovernorate(language = language)
        }
    }

    override suspend fun getCityById(cityId: Int): City? {
        return safeCall(FailException("getCityById")) {
            val language = userPreferencesLocalDataStore.getLatestSelectedAppLanguage().first()
            locationLocalDataSource.getCityById(cityId)?.toCity(language = language)
        }
    }
}