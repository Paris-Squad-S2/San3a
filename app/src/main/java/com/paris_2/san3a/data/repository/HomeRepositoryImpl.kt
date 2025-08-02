package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.domain.GetAllServicesException
import com.paris_2.san3a.domain.GetAvailableJobsException
import com.paris_2.san3a.domain.GetMostRequestedServicesException
import com.paris_2.san3a.domain.SearchServicesException
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val serviceRemoteDataSource: ServiceRemoteDataSource,
) : HomeRepository, BaseRepository() {

    override fun getAllServices(): Flow<List<Service>> {
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
        serviceRemoteDataSource.requestService(requestedService.toDto())
    }

    override fun getMostRequestedServices(): Flow<List<Service>> {
        return serviceRemoteDataSource.getMostRequestedServices()
            .map { dto -> dto.map { it.toEntity() } }
            .catch { throw GetMostRequestedServicesException() }
    }

    override fun getAvailableJobs(): Flow<List<RequestService>> {
        return serviceRemoteDataSource.getAvailableJobs()
            .map { dto -> dto.map { it.toEntity() } }
            .catch { throw GetAvailableJobsException() }
    }


}