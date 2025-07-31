package com.paris_2.san3a.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val firestore: FirebaseFirestore
): HomeRepository {
    override suspend fun getServices(): List<Service> {
        TODO("Not yet implemented")
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