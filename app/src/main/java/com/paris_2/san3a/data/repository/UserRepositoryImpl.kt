package com.paris_2.san3a.data.repository

import android.net.Uri
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.data.source.remote.user.UserRemoteDataSource
import com.paris_2.san3a.domain.CompleteUserSetupException
import com.paris_2.san3a.domain.GetAccountTypeException
import com.paris_2.san3a.domain.GetRecentRelatedJobsException
import com.paris_2.san3a.domain.GetServicesException
import com.paris_2.san3a.domain.GetStatsException
import com.paris_2.san3a.domain.GetUserException
import com.paris_2.san3a.domain.GetUserProgressException
import com.paris_2.san3a.domain.GetUserWorkMediaException
import com.paris_2.san3a.domain.SaveAccountTypeException
import com.paris_2.san3a.domain.SaveLocationException
import com.paris_2.san3a.domain.SavePersonalInfoException
import com.paris_2.san3a.domain.SaveServicesException
import com.paris_2.san3a.domain.SaveWorkShowcaseException
import com.paris_2.san3a.domain.UploadNationalIdImagesException
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.Stats
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource
) : UserRepository, BaseRepository() {
    override suspend fun saveAccountType(phone: String, accountType: AccountType) =
        safeCall(SaveAccountTypeException()) {
            userRemoteDataSource.saveAccountType(phone, accountType)
        }

    override suspend fun getAccountType(phone: String): AccountType =
        safeCall(GetAccountTypeException()) {
            userRemoteDataSource.getAccountType(phone)
        }

    override suspend fun saveServices(
        phone: String,
        services: List<Service>,
        isCraftsman: Boolean
    ) =
        safeCall(SaveServicesException()) {
            userRemoteDataSource.saveServices(phone, services, isCraftsman)
        }

    override fun getServices(phone: String, isCraftsman: Boolean): Flow<List<Service>> =
        userRemoteDataSource.getServices(phone, isCraftsman).map { it.toEntity() }.catch {
            throw GetServicesException()
        }

    override suspend fun saveLocation(phone: String, location: Location) =
        safeCall(SaveLocationException()) {
            userRemoteDataSource.saveLocation(phone, location)
        }

    override suspend fun savePersonalInfo(phone: String, fullName: String, profileUri: Uri?) =
        safeCall(SavePersonalInfoException()) {
            val profileUrl = profileUri?.let { uri ->
                val path = "$PROFILE_IMAGE_PATH/$phone.jpg"
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path)).firstOrNull()
            }
            userRemoteDataSource.savePersonalInfo(phone, fullName, profileUrl)
        }

    override suspend fun saveWorkShowcase(
        phone: String,
        workMedia: List<Uri>?,
        workDescription: String
    ) =
        safeCall(SaveWorkShowcaseException()) {
            val mediaUrls = workMedia?.mapIndexedNotNull { index, uri ->
                val path = "$WORK_SHOWCASE_PATH/$phone/media_$index.jpg"
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path)).firstOrNull()
            }
            userRemoteDataSource.saveWorkShowcase(phone, mediaUrls, workDescription)
        }

    override suspend fun getUserProgress(phone: String): AccountSetupStep =
        safeCall(GetUserProgressException()) {
            userRemoteDataSource.getUserProgress(phone)
        }

    override suspend fun completeUserSetup(phone: String) =
        safeCall(CompleteUserSetupException()) {
            userRemoteDataSource.completeUserSetup(phone)
        }

    override suspend fun getStats(userId: String): Stats {
        return safeCall(GetStatsException()) {
            userRemoteDataSource.getStats(userId).toEntity()
        }
    }

    override fun getRecentRelatedJobs(relatedJobs: List<String>): Flow<List<RequestService>> {
        return userRemoteDataSource.getRecentRelatedJobs(relatedJobs)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw GetRecentRelatedJobsException() }
    }

    override suspend fun uploadNationalIdImages(phone: String, frontUri: Uri?, backUri: Uri?) =
        safeCall(UploadNationalIdImagesException()) {
            val frontUrl = frontUri?.let { uri ->
                val path = "$NATIONAL_ID_PATH/$phone/$FRONT_IMAGE_NAME"
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path)).firstOrNull()
            }

            val backUrl = backUri?.let { uri ->
                val path = "$NATIONAL_ID_PATH/$phone/$BACK_IMAGE_NAME"
                storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
                storageRemoteDataSource.getImagesByPaths(listOf(path)).firstOrNull()
            }
            userRemoteDataSource.saveNationalIdImages(phone, frontUrl, backUrl)
        }

    override suspend fun getUser(phone: String): User =
        safeCall(GetUserException()) {
            userRemoteDataSource.getUser(phone)
        }

    override suspend fun getWorkMedia(phone: String): List<String> =
        safeCall(GetUserWorkMediaException()) {
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