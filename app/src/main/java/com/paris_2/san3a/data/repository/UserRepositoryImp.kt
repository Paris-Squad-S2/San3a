package com.paris_2.san3a.data.repository

import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.repository.UserRemoteDataSource
import com.paris_2.san3a.domain.repository.UserRepository
import com.paris_2.san3a.domain.source.local.UserLocalDataSource
import android.net.Uri
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource

class UserRepositoryImp(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource
) : UserRepository {
    override suspend fun saveAccountType(phone: String, accountType: AccountType) {
        userLocalDataSource.setAccountType(accountType)
        userRemoteDataSource.saveAccountType(phone, accountType)
    }

    override suspend fun saveServices(phone: String, services: List<String>, isCraftsman: Boolean) {
        userRemoteDataSource.saveServices(phone, services, isCraftsman)
    }

    override suspend fun saveLocation(phone: String, location: Location) {
        userRemoteDataSource.saveLocation(phone, location)
    }

    override suspend fun savePersonalInfo(phone: String, fullName: String, profileUri: Uri?) {
        val profileUrl = profileUri?.let { uri ->
            val path = "$PROFILE_IMAGE_PATH/$phone.jpg"
            storageRemoteDataSource.saveImages(listOf(path), listOf(uri))
            val urls = storageRemoteDataSource.getImagesByPaths(listOf(path))
            urls.firstOrNull()
        }
        userRemoteDataSource.savePersonalInfo(phone, fullName, profileUrl)
    }

    override suspend fun saveWorkShowcase(phone: String, workMedia: List<String>, workDescription: String) {
        userRemoteDataSource.saveWorkShowcase(phone, workMedia, workDescription)
    }

    override suspend fun getUserProgress(phone: String): AccountSetupStep {
        return userRemoteDataSource.getUserProgress(phone)
    }

    override suspend fun completeUserSetup(phone: String) {
        userRemoteDataSource.completeUserSetup(phone)
    }

    override suspend fun uploadNationalIdImages(phone: String, frontUri: Uri?, backUri: Uri?) {
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