package com.paris_2.san3a.data.repository

import android.net.Uri
import android.util.Log
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.data.source.remote.user.UserRemoteDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.FailException
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.Stats
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val localDataStore: LocalDataStore
    ) : UserRepository, BaseRepository() {

    override suspend fun addUser(phone: String) =
        safeCall(FailException("Failed to add user")) {
            userRemoteDataSource.addUser(phone)
        }

    override suspend fun saveAccountType(phone: String, accountType: AccountType) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return safeCall(FailException("Failed to save account type")) {
            userRemoteDataSource.saveAccountType(phone, accountType)
        }
    }


    override suspend fun getAccountType(phone: String): AccountType =
        safeCall(FailException("Failed to get account type")) {
            userRemoteDataSource.getAccountType(phone)
        }

    override suspend fun saveServices(
        phone: String,
        services: List<String>,
        isCraftsman: Boolean
    ) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        safeCall(FailException("Failed to save services")) {
            userRemoteDataSource.saveServices(phone, services, isCraftsman)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getServices(phone: String, isCraftsman: Boolean): Flow<List<Service>> {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return localDataStore.isDarkThemeEnabled().flatMapLatest { isDarkModeEnabled ->
            localDataStore.getLatestSelectedAppLanguage().flatMapLatest { language ->
                userRemoteDataSource.getServices(phone, isCraftsman)
                    .map { dtoList -> dtoList.toEntity(isDarkModeEnabled, language) }
                    .catch { throw FailException("Failed to get services for user: $phone, isCraftsman: $isCraftsman") }
            }
        }
    }

    override suspend fun saveLocation(phone: String, location: Location) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        safeCall(FailException("Failed to save location")) {
            userRemoteDataSource.updateLocation(phone, location)
        }
    }

    override suspend fun savePersonalInfo(phone: String, fullName: String, profileUri: Uri?) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        safeCall(FailException("Failed to save personal info")) {
            val profileUrl = profileUri?.let { uri ->
                val path = "$PROFILE_IMAGE_PATH/$phone.jpg"
                Log.d("UserRepositoryImpl", "savePersonalInfo: $path")
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path),listOf(uri)).firstOrNull()
            }
            userRemoteDataSource.updatePersonalInfo(phone, fullName, profileUrl)
        }
    }

    override suspend fun saveWorkShowcase(
        phone: String,
        workMedia: List<Uri>?,
        workDescription: String
    ) =
        safeCall(FailException("Failed to save work showcase")) {
            val mediaUrls = workMedia?.mapIndexedNotNull { index, uri ->
                val path = "$WORK_SHOWCASE_PATH/$phone/media_$index.jpg"
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path),listOf(uri)).firstOrNull()
            }
            userRemoteDataSource.updateWorkShowcase(phone, mediaUrls, workDescription)
        }

    override suspend fun getUserProgress(phone: String): AccountSetupStep =
        safeCall(FailException("Failed to get user progress")) {
            userRemoteDataSource.getUserProgress(phone)
        }

    override suspend fun updateUserProgress(phone: String, step: AccountSetupStep) =
        safeCall(FailException("Failed to update user progress")) {
            userRemoteDataSource.updateUserProgress(phone, step)
        }

    override fun getStats(userId: String): Flow<Stats> {
        return combine(
            userRemoteDataSource.getJobsDoneForCraftsman(userId),
            userRemoteDataSource.getEarningsForCraftsman(userId),
            userRemoteDataSource.getRatingForCraftsman(userId)
        ) { jobsDone, earnings, rating ->
            Stats(
                userId = userId,
                jobsDone = jobsDone,
                earnings = earnings,
                rating = rating
            )
        }.catch {
            throw FailException("Failed to get stats for user: $userId")
        }
    }

    override suspend fun addRatingForCraftsman(
        userId: String,
        craftsmanId: String,
        rating: Float
    ) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        safeCall(FailException("Failed to add rating for craftsman")) {
            userRemoteDataSource.addRatingForCraftsman(userId, craftsmanId, rating)
        }
    }

    override fun getRatingForCraftsman(craftsmanId: String): Flow<Float> {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return userRemoteDataSource.getRatingForCraftsman(craftsmanId)
            .catch { throw FailException("Failed to get rating for craftsman: $craftsmanId") }
    }

    override suspend fun getCustomerRatingOnCraftsman(
        craftsmanId: String,
        userId: String
    ): Float? {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return safeCall(FailException("Failed to get customer rating on craftsman")) {
            userRemoteDataSource.getCustomerRatingOnCraftsman(craftsmanId, userId)
        }
    }

    override suspend fun updateEarningsForCraftsman(
        craftsmanId: String,
        userId: String,
        requestId: String,
        earnings: Double
    ) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        safeCall(FailException("Failed to update earnings for craftsman")) {
            userRemoteDataSource.updateEarningsForCraftsman(
                craftsmanId = craftsmanId,
                userId = userId,
                requestId = requestId,
                earnings = earnings
            )
        }
    }

    override suspend fun incrementJobsDoneForCraftsman(
        craftsmanId: String,
        requestId: String,
        userId: String
    ) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        safeCall(FailException("Failed to increment jobs done for craftsman")) {
            userRemoteDataSource.incrementJobsDoneForCraftsman(craftsmanId, requestId, userId)
        }
    }

    override fun getRecentRelatedJobs(relatedJobs: List<String>): Flow<List<RequestService>> {
        return userRemoteDataSource.getRecentRelatedJobs(relatedJobs)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to get recent related jobs: $relatedJobs") }
    }

    override suspend fun uploadNationalIdImages(phone: String, frontUri: Uri?, backUri: Uri?) {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        safeCall(FailException("Failed to upload national ID images")) {
            val frontUrl = frontUri?.let { uri ->
                val path = "$NATIONAL_ID_PATH/$phone/$FRONT_IMAGE_NAME"
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path),listOf(uri)).firstOrNull()
            }

            val backUrl = backUri?.let { uri ->
                val path = "$NATIONAL_ID_PATH/$phone/$BACK_IMAGE_NAME"
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path),listOf(uri)).firstOrNull()
            }
            userRemoteDataSource.updateNationalIdImages(phone, frontUrl, backUrl)
        }
    }

    override suspend fun getUser(phone: String): User {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return safeCall(FailException("Failed to get user")) {
            userRemoteDataSource.getUser(phone)
        }
    }

    override suspend fun getWorkMedia(phone: String): List<String> =
        safeCall(FailException("Failed to get work media")) {
            userRemoteDataSource.getWorkMedia(phone)
        }

    companion object {
        private const val PROFILE_IMAGE_PATH = "profile_images"
        private const val NATIONAL_ID_PATH = "national_ids"
        private const val FRONT_IMAGE_NAME = "front.jpg"
        private const val BACK_IMAGE_NAME = "back.jpg"
        private const val WORK_SHOWCASE_PATH = "work_showcase"
    }
}