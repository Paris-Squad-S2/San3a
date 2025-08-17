package com.paris_2.san3a.data.repository

import android.util.Log
import androidx.core.net.toUri
import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.GetAllServicesException
import com.paris_2.san3a.domain.GetAvailableJobsException
import com.paris_2.san3a.domain.GetMostRequestedServicesException
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.RequestServiceException
import com.paris_2.san3a.domain.SearchServicesException
import com.paris_2.san3a.domain.UpdateNumOfRequestsException
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val serviceRemoteDataSource: ServiceRemoteDataSource,
    private val firebaseStorageRemoteDataSource: StorageRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker
) : HomeRepository, BaseRepository() {

    override fun getAllServices(): Flow<List<Service>> {

        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return serviceRemoteDataSource.getAllServices()
            .map { dtoList -> dtoList.map { it.toEntity() } }
            .catch { throw GetAllServicesException() }
    }

    override fun searchServices(query: String): Flow<List<Service>> {
        return serviceRemoteDataSource.searchServices(query)
            .map { dto -> dto.map { it.toEntity() } }
            .catch { throw SearchServicesException() }
    }

    override suspend fun requestService(requestedService: RequestService) {
        safeCall(RequestServiceException(requestedService)){
            val imageUris = requestedService.image
            val imageUrls = if (imageUris.isNotEmpty()) {
                val paths = imageUris.map { uri ->
                    "${requestedService.title}/${
                        uri.toUri().path?.substringAfterLast("/") ?: ""
                    }.jpg"
                }
                firebaseStorageRemoteDataSource.saveImages(
                    paths,
                    imageUris.map { it.toUri() }
                )
                firebaseStorageRemoteDataSource.getImagesByPaths(paths,imageUris.map { it.toUri() })
            } else {
                emptyList()
            }

            serviceRemoteDataSource.requestService(requestedService.toDto(imageUrls))
        }
    }

    override fun getMostRequestedServices(): Flow<List<Service>> {
        return serviceRemoteDataSource.getMostRequestedServices()
            .map { dto -> dto.map { it.toEntity() } }
            .catch { throw GetMostRequestedServicesException() }

    }

    override fun getAvailableJobs(): Flow<List<RequestService>> {
        return serviceRemoteDataSource.getAvailableJobs()
            .map { dto -> dto.map { it.toEntity() } }
            .catch {
                Log.d("HomeRepositoryImpl", "Error fetching available jobs: ${it.message}")
                throw GetAvailableJobsException()
            }
    }

    override suspend fun updateNumOfRequestService(serviceId: String) {
        return try {
            serviceRemoteDataSource.updateNumOfRequestService(serviceId)
        }catch (e: Exception) {
            throw UpdateNumOfRequestsException()
        }
    }


}