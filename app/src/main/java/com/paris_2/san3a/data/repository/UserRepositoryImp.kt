package com.paris_2.san3a.data.repository

import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.repository.UserRemoteDataSource
import com.paris_2.san3a.domain.repository.UserRepository
import android.net.Uri
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.domain.CompleteUserSetupException
import com.paris_2.san3a.domain.GetAccountTypeException
import com.paris_2.san3a.domain.GetUserProgressException
import com.paris_2.san3a.domain.SaveAccountTypeException
import com.paris_2.san3a.domain.SaveLocationException
import com.paris_2.san3a.domain.SavePersonalInfoException
import com.paris_2.san3a.domain.SaveServicesException
import com.paris_2.san3a.domain.SaveWorkShowcaseException
import com.paris_2.san3a.domain.UploadNationalIdImagesException

class UserRepositoryImp(
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

    override suspend fun saveServices(phone: String, services: List<String>, isCraftsman: Boolean) =
        safeCall(SaveServicesException()) {
            userRemoteDataSource.saveServices(phone, services, isCraftsman)
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

    override suspend fun saveWorkShowcase(phone: String, workMedia: List<String>, workDescription: String) =
        safeCall(SaveWorkShowcaseException()) {
            userRemoteDataSource.saveWorkShowcase(phone, workMedia, workDescription)

        }

    override suspend fun getUserProgress(phone: String): AccountSetupStep =
        safeCall(GetUserProgressException()) {
            userRemoteDataSource.getUserProgress(phone)
        }

    override suspend fun completeUserSetup(phone: String) =
        safeCall(CompleteUserSetupException()) {
            userRemoteDataSource.completeUserSetup(phone)
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

    companion object {
        private const val PROFILE_IMAGE_PATH = "profile_images"
        private const val NATIONAL_ID_PATH = "national_ids"
        private const val FRONT_IMAGE_NAME = "front.jpg"
        private const val BACK_IMAGE_NAME = "back.jpg"
    }
}