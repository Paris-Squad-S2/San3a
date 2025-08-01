package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val serviceRemoteDataSource: ServiceRemoteDataSource,
): HomeRepository {

    override suspend fun getAllServices(): Flow<List<Service>> {
        return serviceRemoteDataSource.getAllServices().map { dtoList -> dtoList.map { it.toEntity() } }
    }

    override suspend fun getMostRequestedServices() {
        TODO("Not yet implemented")
    }

    override suspend fun requestService() {
        TODO("Not yet implemented")
    }

    override suspend fun getStats() {
        TODO("Not yet implemented")
    }

    override suspend fun getAvailableJobs() {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentRelatedJobs() {
        TODO("Not yet implemented")
    }
}