package com.paris_2.san3a.data.source.remote.user

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.SetOperation
import com.paris_2.san3a.data.service.firestore.WriteOperation
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.StatsDto
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class UserRemoteDataSourceImpl(
    private val fireStoreService: FireStoreService,
) : UserRemoteDataSource {

    override suspend fun addUser(phone: String) {
        val data = mapOf(
            "phone" to phone,
            "currentStep" to AccountSetupStep.ACCOUNT_TYPE.name,
        )
        fireStoreService.setDoc(path = "$USERS_COLLECTION/$phone", data = data)
    }

    override suspend fun saveAccountType(phone: String, accountType: AccountType) {
        val data = mapOf(
            "accountType" to accountType.name,
        )
        updateUserData(phone, data)
    }

    override suspend fun updateUserProgress(phone: String, step: AccountSetupStep) {
        val data = mapOf("currentStep" to step.name)
        updateUserData(phone, data)
    }

    override suspend fun getAccountType(phone: String): AccountType {
        val userData = fireStoreService.getDoc(
            path = "$USERS_COLLECTION/$phone",
            fromJson = { data, _ -> data["accountType"]?.toString() }
        )
        return AccountType.entries.find { it.name == userData } ?: AccountType.CUSTOMER
    }

    override suspend fun saveServices(
        phone: String,
        services: List<Service>,
        isCraftsman: Boolean
    ) {
        val path = if (isCraftsman) {
            "$USERS_COLLECTION/$phone/$OFFERED_SERVICES_COLLECTION"
        } else {
            "$USERS_COLLECTION/$phone/$REQUESTED_SERVICES_PATH"
        }
        fireStoreService.clearCollection(path = path)
        val operations = mutableListOf<WriteOperation>()
        services.forEach { service ->
            operations.add(
                SetOperation(
                    path = "$path/${service.id}",
                    data = mapOf()
                )
            )
        }
        fireStoreService.batchWrite(operations)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getServices(phone: String, isCraftsman: Boolean): Flow<List<ServiceDto>> {
        val path = if (isCraftsman) {
            "$USERS_COLLECTION/$phone/$OFFERED_SERVICES_COLLECTION"
        } else {
            "$USERS_COLLECTION/$phone/$REQUESTED_SERVICES_PATH"
        }
        return fireStoreService.streamCollection(
            path = path,
            fromJson = ::getServices
        ).let { docsFlow ->
            docsFlow.flatMapLatest { docsList ->
                fireStoreService.streamCollection(
                    path = SERVICES_COLLECTION,
                    fromJson = ServiceDto::fromJson,
                    queryBuilder = { query ->
                        query.whereIn(FieldPath.documentId(), docsList)
                    }
                )
            }
        }
    }

    fun getServices(data: Map<String, Any>, serviceId: String): String {
        return serviceId
    }

    override suspend fun updateLocation(phone: String, location: Location) {
        val data = mapOf(
            "location" to mapOf(
                "cityName" to location.cityName,
                "government" to location.government,
                "addressInDetails" to location.addressInDetails
            ),
        )
        updateUserData(phone, data)
    }

    override suspend fun updatePersonalInfo(phone: String, fullName: String, profilePhoto: String?) {
        val data = mutableMapOf<String, Any>(
            "fullName" to fullName,
        )
        if (profilePhoto != null) {
            data["profilePhoto"] = profilePhoto
        }
        updateUserData(phone, data)
    }

    override suspend fun updateWorkShowcase(phone: String, workMedia: List<String>?, workDescription: String) {
        val data = mutableMapOf<String, Any>(
            "workDescription" to workDescription,
        )
        workMedia?.let { data["workMedia"] = it }
        updateUserData(phone, data)
    }

    override suspend fun updateNationalIdImages(phone: String, frontUrl: String?, backUrl: String?) {
        val data = mutableMapOf<String, Any>().apply {
            frontUrl?.let { this["nationalIdFrontImage"] = it }
            backUrl?.let { this["nationalIdBackImage"] = it }
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


    override suspend fun getUser(phone: String): User {
        val userData = fireStoreService.getDoc(
            path = "$USERS_COLLECTION/$phone",
            fromJson = { data, _ -> data }
        ) ?: throw Exception("User not found with phone number: $phone")

        return User(
            id = phone,
            phone = phone,
            fullName = userData["fullName"]?.toString() ?: "",
            profilePhoto = userData["profilePhoto"]?.toString() ?: "",
            nationalIdFrontImage = userData["nationalIdFrontImage"]?.toString() ?: "",
            nationalIdBackImage = userData["nationalIdBackImage"]?.toString() ?: "",
            workDescription = userData["workDescription"]?.toString() ?: "",
            accountType = AccountType.entries.find {
                it.name == userData["accountType"]?.toString()
            } ?: AccountType.CUSTOMER,
            location = (userData["location"] as? Map<*, *>)?.let { locationData ->
                Location(
                    government = locationData["government"]?.toString() ?: "",
                    cityName = locationData["cityName"]?.toString() ?: "",
                    addressInDetails = locationData["addressInDetails"]?.toString() ?: ""
                )
            } ?: Location("", "", ""),
        )
    }

    override suspend fun getWorkMedia(phone: String): List<String> {
        val userData = fireStoreService.getDoc(
            path = "$USERS_COLLECTION/$phone",
            fromJson = { data, _ -> data }
        ) ?: throw Exception("User not found with phone number: $phone")

        return (userData["workMedia"] as? List<*>)?.filterIsInstance<String>() ?: emptyList()
    }

    private suspend fun updateUserData(phone: String, data: Map<String, Any>) {
        fireStoreService.updateDoc(path = "$USERS_COLLECTION/$phone", data = data)
        Log.d("AccountSetup", "Account type saved successfully at $USERS_COLLECTION/$phone with data: $data")
    }

    override suspend fun getStats(userId: String): StatsDto {
        return try {
            fireStoreService.getDoc(
                path = "$CRAFTSMAN_STATUS_COLLECTION/$userId",
                fromJson = StatsDto::fromJson
            ) ?: StatsDto(userId, 0, 0.0, 0.0)
        } catch (_: Exception) {
            addStats(
                userId,
                StatsDto(userId, 0, 0.0, 0.0)
            )
            StatsDto(userId, 0, 0.0, 0.0)
        }
    }

    suspend fun addStats(userId: String, stats: StatsDto) {
        fireStoreService.setDoc(
            path = "$CRAFTSMAN_STATUS_COLLECTION/$userId",
            data = stats.toJson()
        )
    }

    override suspend fun updateStats(userId: String, stats: StatsDto) {
        fireStoreService.updateDoc(
            path = "$CRAFTSMAN_STATUS_COLLECTION/$userId",
            data = stats.toJson()
        )
    }

    override fun getRecentRelatedJobs(relatedJobs: List<String>): Flow<List<RequestServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
            queryBuilder = { query ->
                query.whereIn("title", relatedJobs)
            }
        )
    }

    companion object {
        const val USERS_COLLECTION = "users"
        const val CRAFTSMAN_STATUS_COLLECTION = "craftsmen"
        const val SERVICE_REQUESTS_COLLECTION = "service_requests"
        const val OFFERED_SERVICES_COLLECTION = "offeredServices"
        const val REQUESTED_SERVICES_PATH = "requestedServices"
        const val SERVICES_COLLECTION = "services"
    }
}