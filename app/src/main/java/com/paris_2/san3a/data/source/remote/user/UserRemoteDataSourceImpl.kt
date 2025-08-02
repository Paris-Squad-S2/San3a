package com.paris_2.san3a.data.source.remote.user

import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.StatsDto
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import kotlinx.coroutines.flow.Flow

class UserRemoteDataSourceImpl(
    private val fireStoreService: FireStoreService,
) : UserRemoteDataSource {

    override suspend fun saveAccountType(phone: String, accountType: AccountType) {
        val data = mapOf(
            "accountType" to accountType.name,
            "currentStep" to AccountSetupStep.SERVICES.name,
            "phone" to phone
        )
        updateUserData(phone, data)
    }

    override suspend fun getAccountType(phone: String): AccountType {
        val userData = fireStoreService.getDoc(
            path = "$USERS_COLLECTION/$phone",
            fromJson = { data, _ -> data["accountType"]?.toString() }
        )
        return AccountType.entries.find { it.name == userData } ?: AccountType.CUSTOMER
    }

    override suspend fun saveServices(phone: String, services: List<String>, isCraftsman: Boolean) {
        val data = if (isCraftsman) {
            mapOf(
                "offeredServices" to services,
                "currentStep" to AccountSetupStep.LOCATION.name
            )
        } else {
            mapOf(
                "requestedServices" to services,
                "currentStep" to AccountSetupStep.LOCATION.name
            )
        }
        updateUserData(phone, data)
    }

    override suspend fun saveLocation(phone: String, location: Location) {
        val data = mapOf(
            "location" to mapOf(
                "latitude" to location.latitude,
                "longitude" to location.longitude,
                "cityName" to location.cityName,
                "countryName" to location.countryName
            ),
            "currentStep" to AccountSetupStep.PERSONAL_INFO.name
        )
        updateUserData(phone, data)
    }

    override suspend fun savePersonalInfo(phone: String, fullName: String, profilePhoto: String?) {
        val data = mutableMapOf<String, Any>(
            "fullName" to fullName,
            "currentStep" to AccountSetupStep.WORK_SHOWCASE.name
        )
        if (profilePhoto != null) {
            data["profilePhoto"] = profilePhoto
        }
        updateUserData(phone, data)
    }

    override suspend fun saveWorkShowcase(phone: String, workMedia: List<String>, workDescription: String) {
        val data = mapOf(
            "workMedia" to workMedia,
            "workDescription" to workDescription,
            "currentStep" to AccountSetupStep.UPLOAD_NATIONAL_ID.name
        )
        updateUserData(phone, data)
    }

    override suspend fun saveNationalIdImages(phone: String, frontUrl: String?, backUrl: String?) {
        val data = mutableMapOf<String, Any>().apply {
            frontUrl?.let { this["nationalIdFrontImage"] = it }
            backUrl?.let { this["nationalIdBackImage"] = it }
            this["currentStep"] = AccountSetupStep.COMPLETED.name
        }
        updateUserData(phone, data)
    }

    override suspend fun getUserProgress(phone: String): AccountSetupStep {
        return try {
            val userData = fireStoreService.getDoc(
                path = "$USERS_COLLECTION/$phone",
                fromJson = { data, _ -> data["currentStep"]?.toString() }
            )
            runCatching { AccountSetupStep.valueOf(userData ?: AccountSetupStep.ACCOUNT_TYPE.name) }
                .getOrDefault(AccountSetupStep.ACCOUNT_TYPE)
        } catch (_: Exception) {
            AccountSetupStep.ACCOUNT_TYPE
        }
    }

    override suspend fun completeUserSetup(phone: String) {
        val data = mapOf(
            "setupCompleted" to true,
            "currentStep" to AccountSetupStep.COMPLETED.name
        )
        updateUserData(phone, data)
    }

    private suspend fun updateUserData(phone: String, data: Map<String, Any>) {
        fireStoreService.updateDoc(path = "$USERS_COLLECTION/$phone", data = data)
    }

    override suspend fun getStats(userId: String): StatsDto? {
        return fireStoreService.getDoc(
            path = "$USERS_COLLECTION/$STATS_COLLECTION/$userId",
            fromJson = StatsDto.Companion::fromJson
        )
    }

    override fun getRecentRelatedJobs(relatedJob: String): Flow<List<RequestServiceDto>> {
        return fireStoreService.streamCollection(
            path = REQUESTED_SERVICES_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("relatedJob", relatedJob)
            }
        )
    }

    companion object {
        const val USERS_COLLECTION = "users"
        const val STATS_COLLECTION = "stats"
        const val REQUESTED_SERVICES_COLLECTION = "requestedServices"
    }
}