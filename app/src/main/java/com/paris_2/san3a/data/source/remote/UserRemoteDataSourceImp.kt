package com.paris_2.san3a.data.source.remote

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.repository.UserRemoteDataSource

class UserRemoteDataSourceImp(
    private val fireStoreService: FireStoreService,
) : UserRemoteDataSource {

    override suspend fun saveAccountType(phone: String, accountType: AccountType) {
        val data = mapOf(
            "accountType" to accountType.name,
            "currentStep" to AccountSetupStep.SERVICES.name,
            "phone" to phone
        )
        fireStoreService.setDoc(documentPath = "$USERS_COLLECTION/$phone", data = data)
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
        services.forEach { service ->
            val collectionPath = "$USERS_COLLECTION/$phone/services/${service.id}"
            fireStoreService.setDoc(documentPath = collectionPath, data = mapOf<String, Any>())
        }
    }

    override suspend fun getServices(phone: String): List<ServiceDto> {
        return fireStoreService.getCollection(path = "$USERS_COLLECTION/$phone/services", fromJson = ::getServices).let { docs ->
            fireStoreService.getCollection(path = "services", fromJson = ServiceDto::fromJson, queryBuilder = { query ->
                query.whereIn(FieldPath.documentId(), docs)
            })
        }
    }

    fun getServices(data: Map<String, Any>, serviceId: String): String {
        return serviceId
    }

    override suspend fun saveLocation(phone: String, location: Location) {
        val data = mapOf(
            "location" to mapOf(
                "cityName" to location.cityName,
                "government" to location.government
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

    override suspend fun saveWorkShowcase(phone: String, workMedia: List<String>?, workDescription: String) {
        val data = mutableMapOf<String, Any>(
            "workDescription" to workDescription,
            "currentStep" to AccountSetupStep.UPLOAD_NATIONAL_ID.name
        )
        workMedia?.let { data["workMedia"] = it }
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
                    cityName = locationData["cityName"]?.toString() ?: ""
                )
            } ?: Location("", "")
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

    companion object {
        const val USERS_COLLECTION = "users"
    }
}