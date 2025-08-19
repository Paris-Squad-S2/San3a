package com.paris_2.san3a.data.repository

import android.util.Log
import androidx.core.net.toUri
import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.FailException
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.ServicesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class ServicesRepositoryImpl(
    private val serviceRemoteDataSource: ServiceRemoteDataSource,
    private val firebaseStorageRemoteDataSource: StorageRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val localDataStore: LocalDataStore
) : ServicesRepository, BaseRepository() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllServices(): Flow<List<Service>> {

        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return localDataStore.isDarkThemeEnabled().flatMapLatest { isDarkModeEnabled ->
            localDataStore.getLatestSelectedAppLanguage().flatMapLatest { language ->
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
            val isDarkModeEnabled = localDataStore.isDarkThemeEnabled().first()
            val language = localDataStore.getLatestSelectedAppLanguage().first()
            serviceRemoteDataSource.getServiceById(serviceId)?.toEntity(
                isDarkTheme = isDarkModeEnabled,
                language = language
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchServices(query: String): Flow<List<Service>> {
        validateNetworkConnection()
        return localDataStore.getLatestSelectedAppLanguage().flatMapLatest { language ->
            localDataStore.isDarkThemeEnabled().flatMapLatest { isDarkModeEnabled ->
                serviceRemoteDataSource.searchServices(query)
                    .map { it.toEntity(isDarkModeEnabled, language) }
                    .catch { throw FailException("searchServices") }
            }
        }
    }

    override suspend fun requestService(requestedService: RequestService) {
        validateNetworkConnection()
        safeCall(FailException("requestService")) {
            val imageUris = requestedService.image
            val imageUrls = if (imageUris.isNotEmpty()) {
                val paths = imageUris.map { uri ->
                    "${requestedService.title}/${uri.toUri().path?.substringAfterLast("/") ?: ""}.jpg"
                }
                firebaseStorageRemoteDataSource.saveImages(paths, imageUris.map { it.toUri() })
                firebaseStorageRemoteDataSource.getImagesByPaths(
                    paths,
                    imageUris.map { it.toUri() })
            } else {
                emptyList()
            }

            serviceRemoteDataSource.requestService(requestedService.toDto(imageUrls))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMostRequestedServices(): Flow<List<Service>> {
        validateNetworkConnection()
        return localDataStore.isDarkThemeEnabled().flatMapLatest { isDarkModeEnabled ->
            localDataStore.getLatestSelectedAppLanguage().flatMapLatest { language ->
                serviceRemoteDataSource.getMostRequestedServices()
                    .map { it.toEntity(isDarkModeEnabled, language) }
                    .catch { throw FailException("getMostRequestedServices") }
            }
        }
    }

    override fun getAvailableJobs(): Flow<List<RequestService>> {
        validateNetworkConnection()
        return serviceRemoteDataSource.getAvailableJobs()
            .map { dto -> dto.map { it.toEntity() } }
            .catch {
                Log.d("HomeRepositoryImpl", "Error fetching available jobs: ${it.message}")
                throw FailException("getAvailableJobs")
            }
    }

    override suspend fun updateNumOfRequestService(serviceId: String) {
        validateNetworkConnection()
        safeCall(FailException("updateNumOfRequestService")) {
            serviceRemoteDataSource.updateNumOfRequestService(serviceId)
        }
    }
}