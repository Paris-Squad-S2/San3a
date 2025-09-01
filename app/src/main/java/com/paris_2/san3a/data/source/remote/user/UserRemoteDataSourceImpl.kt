package com.paris_2.san3a.data.source.remote.user

import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.SetOperation
import com.paris_2.san3a.data.service.firestore.WriteOperation
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.OtpDto
import com.paris_2.san3a.data.source.remote.user.dto.OtpMessageDto
import com.paris_2.san3a.data.source.remote.user.service.AuthApiServices
import com.paris_2.san3a.data.utils.roundFloat
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.exceptions.InvalidNumberException
import com.paris_2.san3a.domain.exceptions.ServerException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRemoteDataSourceImpl(
    private val fireStoreService: FireStoreService,
    private val authApiServices: AuthApiServices
) : UserRemoteDataSource {

    override suspend fun sendOtpMessage(message: OtpMessageDto): OtpDto {
        return try {
            val response = authApiServices.sendOtpMessage(message)
            handleOtpResponse(response)
        } catch (e: retrofit2.HttpException) {
            throw mapOtpException(e.code())
        }
    }

    private fun handleOtpResponse(response: retrofit2.Response<OtpDto>): OtpDto {
        return if (response.isSuccessful) {
            response.body() ?: OtpDto()
        } else {
            throw mapOtpException(response.code())
        }
    }

    private fun mapOtpException(code: Int): Exception {
        return when (code) {
            400 -> InvalidNumberException()
            else -> ServerException()
        }
    }

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

    override suspend fun saveServices(
        phone: String,
        services: List<String>,
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
                    path = "$path/${service}",
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
            fromJson = { _, serviceId -> serviceId },
        ).let { docsFlow ->
            docsFlow.flatMapLatest { docsList ->
                fireStoreService.streamCollection(
                    path = SERVICES_COLLECTION,
                    fromJson = ServiceDto::fromJson,
                ).map { allServices ->
                    allServices.filter { service -> docsList.contains(service.id) } +
                            allServices.filter { service -> !docsList.contains(service.id) }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserSelectedServices(
        phone: String,
        isCraftsman: Boolean
    ): Flow<List<ServiceDto>> {
        val path = if (isCraftsman) {
            "$USERS_COLLECTION/$phone/$OFFERED_SERVICES_COLLECTION"
        } else {
            "$USERS_COLLECTION/$phone/$REQUESTED_SERVICES_PATH"
        }
        return fireStoreService.streamCollection(
            path = path,
            fromJson = { _, serviceId -> serviceId },
        ).let { docsFlow ->
            docsFlow.flatMapLatest { docsList ->
                if (docsList.isNotEmpty()) {
                    fireStoreService.streamCollection(
                        path = SERVICES_COLLECTION,
                        fromJson = ServiceDto::fromJson,
                        queryBuilder = { query ->
                            query.whereIn(FieldPath.documentId(), docsList)
                        }
                    )
                } else {
                    flow { emit(emptyList()) }
                }
            }
        }
    }

    override suspend fun updateLocation(phone: String, location: Location) {
        val data = mapOf(
            "location" to mapOf(
                "cityId" to location.cityId,
                "governmentId" to location.governmentId,
                "addressInDetails" to location.addressInDetails
            ),
        )
        updateUserData(phone, data)
    }

    override suspend fun updatePersonalInfo(
        phone: String,
        fullName: String,
        profilePhoto: String?
    ) {
        val data = mutableMapOf<String, Any>(
            "fullName" to fullName,
        )
        if (profilePhoto != null) {
            data["profilePhoto"] = profilePhoto
        }
        updateUserData(phone, data)
    }

    override suspend fun updateWorkShowcase(
        phone: String,
        workMedia: List<String>?,
        workDescription: String
    ) {
        val data = mutableMapOf<String, Any>(
            "workDescription" to workDescription,
        )
        workMedia?.let { data["workMedia"] = it }
        updateUserData(phone, data)
    }

    override suspend fun updateNationalIdImages(
        phone: String,
        frontUrl: String?,
        backUrl: String?
    ) {
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
        return fireStoreService.getDoc(
            path = "$USERS_COLLECTION/$phone",
            fromJson = User::fromJson
        ) ?: throw Exception("User not found with phone number: $phone")
    }

    override suspend fun getWorkMedia(phone: String): List<String> {
        val userData = fireStoreService.getDoc(
            path = "$USERS_COLLECTION/$phone",
            fromJson = { data, _ -> data }
        ) ?: throw Exception("User not found with phone number: $phone")

        return (userData["workMedia"] as? List<*>)?.filterIsInstance<String>() ?: emptyList()
    }

    override suspend fun addRatingForCraftsman(
        userId: String,
        craftsmanId: String,
        offerId: String,
        rating: Float,
    ) {
        val data = mapOf(
            "rating" to rating,
            "offerId" to offerId,
            "userId" to userId,
            "timestamp" to System.currentTimeMillis()
        )
        fireStoreService.setDoc(
            path = "$USERS_COLLECTION/$craftsmanId/$RATINGS_COLLECTION/$userId-$offerId",
            data = data
        )
        Log.d(
            "AccountSetup",
            "Rating added successfully for craftsman $craftsmanId by user $userId"
        )
    }

    override fun getRatingForCraftsman(craftsmanId: String): Flow<Float> {
        return fireStoreService.streamCollection(
            path = "$USERS_COLLECTION/$craftsmanId/$RATINGS_COLLECTION",
            fromJson = { data, _ -> (data["rating"] as? Number)?.toFloat() }
        ).filterNotNull().map { ratings ->
            if (ratings.isNotEmpty()) {
                ratings.mapNotNull { it }
                    .average()
                    .toFloat()
                    .roundFloat()
            } else 0f
        }
    }


    override fun getCustomerRatingOnCraftsman(
        craftsmanId: String,
        offerId: String,
        userId: String,
    ): Flow<Float?> {
        return fireStoreService.streamDoc(
            path = "$USERS_COLLECTION/$craftsmanId/$RATINGS_COLLECTION/$userId-$offerId",
            fromJson = { data, _ -> (data["rating"] as? Number)?.toFloat()?.roundFloat() }
        )
    }

    override suspend fun updateEarningsForCraftsman(
        craftsmanId: String,
        userId: String,
        requestId: String,
        earnings: Double
    ) {
        val data = mapOf(
            "earnings" to earnings
        )
        fireStoreService.setDoc(
            path = "$USERS_COLLECTION/$craftsmanId/$EARNINGS_COLLECTION/$userId-$requestId",
            data = data
        )
        Log.d(
            "AccountSetup",
            "Earnings updated successfully for craftsman $craftsmanId for request $requestId by user $userId"
        )
    }

    override fun getEarningsForCraftsman(craftsmanId: String): Flow<Double> {
        val earnings = fireStoreService.streamCollection(
            path = "$USERS_COLLECTION/$craftsmanId/$EARNINGS_COLLECTION",
            fromJson = { data, _ -> (data["earnings"] as? Number)?.toDouble() }
        )
        return flow {
            val earningsList = earnings.filterNotNull().firstOrNull() ?: emptyList()
            val total = earningsList.sumOf { it ?: 0.0 }
            emit(total)
        }
    }

    override suspend fun incrementJobsDoneForCraftsman(
        craftsmanId: String,
        requestId: String,
        userId: String
    ) {
        fireStoreService.setDoc(
            path = "$USERS_COLLECTION/$craftsmanId/$JOBS_DONE_COLLECTION/$requestId",
            data = mapOf("userId" to userId)
        )
        Log.d(
            "AccountSetup",
            "Job done incremented successfully for craftsman $craftsmanId for request $requestId by user $userId"
        )
    }

    override fun getJobsDoneForCraftsman(craftsmanId: String): Flow<Int> {
        return fireStoreService.streamCountOfCollection(
            path = "$USERS_COLLECTION/$craftsmanId/$JOBS_DONE_COLLECTION"
        )
    }

    override suspend fun saveDeviceToken(userId: String, token: String) {
        fireStoreService.setDoc(
            path = "$DEVICE_TOKENS_COLLECTION/$userId",
            data = mapOf(
                "token" to token,
                "userId" to userId,
            ),
        )
    }

    override suspend fun getDeviceToken(userId: String): String? {
        return fireStoreService.getDoc(
            path = "$DEVICE_TOKENS_COLLECTION/$userId",
            fromJson = { data, _ -> data["token"]?.toString() ?: "" },
        )
    }

    private suspend fun updateUserData(phone: String, data: Map<String, Any>) {
        fireStoreService.updateDoc(path = "$USERS_COLLECTION/$phone", data = data)
        Log.d(
            "AccountSetup",
            "Account type saved successfully at $USERS_COLLECTION/$phone with data: $data"
        )
    }


    companion object {
        private const val USERS_COLLECTION = "users"
        private const val OFFERED_SERVICES_COLLECTION = "offeredServices"
        private const val REQUESTED_SERVICES_PATH = "requestedServices"
        private const val SERVICES_COLLECTION = "services"
        private const val RATINGS_COLLECTION = "ratings"
        private const val EARNINGS_COLLECTION = "earnings"
        private const val JOBS_DONE_COLLECTION = "jobs_done"
        private const val DEVICE_TOKENS_COLLECTION = "device_tokens"
    }
}