package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toCities
import com.paris_2.san3a.data.mapper.toStates
import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.data.source.local.location.LocationLocalDataSource
import com.paris_2.san3a.domain.NoCitiesFoundException
import com.paris_2.san3a.domain.NoGovernmentsFoundException
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
import com.paris_2.san3a.domain.repository.LocationRepository
import kotlinx.coroutines.flow.first

class LocationRepositoryImp(
    private val locationLocalDataSource: LocationLocalDataSource,
    private val localDataStore: LocalDataStore
) : LocationRepository, BaseRepository() {

    override suspend fun getGovernments(): List<Governorate> {
        return safeCall(NoGovernmentsFoundException()) {
            val language = localDataStore.getLatestSelectedAppLanguage().first()
            locationLocalDataSource.getGovernorates().toStates(language = language)
        }
    }

    override suspend fun getCitiesInGovernment(governorateId: Int): List<City> {
        return safeCall(NoCitiesFoundException()) {
            val language = localDataStore.getLatestSelectedAppLanguage().first()
            locationLocalDataSource.getCities(governorateId).toCities(language = language)
        }
    }
}