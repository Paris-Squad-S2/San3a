package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location

interface UserRemoteDataSource {
    suspend fun saveWorkShowcase(phone: String, workMedia: List<String>, workDescription: String)

    suspend fun getUserProgress(phone: String): AccountSetupStep

    suspend fun saveAccountType(phone: String, accountType: AccountType)

    suspend fun getAccountType(phone: String) :AccountType

    suspend fun saveServices(phone: String, services: List<String>, isCraftsman: Boolean)

    suspend fun saveLocation(phone: String, location: Location)

    suspend fun savePersonalInfo(phone: String, fullName: String, profilePhoto: String?)

    suspend fun saveNationalIdImages(phone: String, frontUrl: String?, backUrl: String?)

    suspend fun completeUserSetup(phone: String)
}