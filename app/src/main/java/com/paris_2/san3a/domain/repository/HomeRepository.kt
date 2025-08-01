package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Service
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAllServices(): Flow<List<Service>>
    fun searchServices(query: String): Flow<List<Service>>
}