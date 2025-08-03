package com.paris_2.san3a.domain.usecase

import android.net.Uri
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.UserRepository

class SetUpAccountUseCase(
    private val userRepository: UserRepository
) {
    suspend fun saveAccountType(phone: String, accountType: AccountType) {
        userRepository.saveAccountType(phone, accountType)
    }

    suspend fun uploadNationalIdImages(phone: String, frontUri: Uri?, backUri: Uri?) {
        userRepository.uploadNationalIdImages(phone, frontUri, backUri)
    }

    suspend fun saveServices(phone: String, services: List<Service>, isCraftsman: Boolean) {
        userRepository.saveServices(phone, services, isCraftsman)
    }

    suspend fun saveLocation(phone: String, location: Location) {
        userRepository.saveLocation(phone, location)
    }

    suspend fun savePersonalInfo(phone: String, fullName: String, profileUri: Uri?) {
        userRepository.savePersonalInfo(phone, fullName, profileUri)
    }

    suspend fun saveWorkShowcase(phone: String, workMedia: List<Uri>?, workDescription: String) {
        userRepository.saveWorkShowcase(phone, workMedia, workDescription)
    }

    suspend fun getUserProgress(phone: String): AccountSetupStep {
        return userRepository.getUserProgress(phone)
    }

    suspend fun completeUserSetup(phone: String) {
        userRepository.completeUserSetup(phone)
    }
}