package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.source.local.UserPreferencesLocalDataStore
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.exceptions.FailException
import com.paris_2.san3a.domain.repository.ServicesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class ServicesRepositoryImpl(
    private val serviceRemoteDataSource: ServiceRemoteDataSource,
    private val userPreferencesLocalDataStore: UserPreferencesLocalDataStore
) : ServicesRepository, BaseRepository() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllServices(): Flow<List<Service>> {
        validateNetworkConnection()
        return userPreferencesLocalDataStore.isDarkThemeEnabled()
            .flatMapLatest { isDarkModeEnabled ->
                userPreferencesLocalDataStore.getLatestSelectedAppLanguage()
                    .flatMapLatest { language ->
                        serviceRemoteDataSource.getAllServices()
                            .map { dtoList ->
                                dtoList.toEntity(
                                    isDarkTheme = isDarkModeEnabled,
                                    language = language
                                )
                            }
                            .catch { throw FailException("getAllServices") }
                    }
            }
    }

    override suspend fun getServiceById(serviceId: String): Service? {
        validateNetworkConnection()
        return safeCall(FailException("getServiceById")) {
            val isDarkModeEnabled = userPreferencesLocalDataStore.isDarkThemeEnabled().first()
            val language = userPreferencesLocalDataStore.getLatestSelectedAppLanguage().first()
            serviceRemoteDataSource.getServiceById(serviceId)?.toEntity(
                isDarkTheme = isDarkModeEnabled,
                language = language
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMostRequestedServices(): Flow<List<Service>> {
        validateNetworkConnection()
        return userPreferencesLocalDataStore.isDarkThemeEnabled()
            .flatMapLatest { isDarkModeEnabled ->
                userPreferencesLocalDataStore.getLatestSelectedAppLanguage()
                    .flatMapLatest { language ->
                        serviceRemoteDataSource.getMostRequestedServices()
                            .map { it.toEntity(isDarkModeEnabled, language) }
                            .catch { throw FailException("getMostRequestedServices") }
                    }
            }
    }

    override suspend fun updateNumOfRequestService(serviceId: String) {
        validateNetworkConnection()
        safeCall(FailException("updateNumOfRequestService")) {
            serviceRemoteDataSource.updateNumOfRequestService(serviceId)
        }
    }
}