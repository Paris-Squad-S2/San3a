package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import android.net.Uri
import com.paris_2.san3a.domain.entity.Stats

interface UserRepository {
    suspend fun saveAccountType(phone: String, accountType: AccountType)
    suspend fun getAccountType(phone: String) : AccountType
    suspend fun saveServices(phone: String, services: List<String>, isCraftsman: Boolean)
    suspend fun saveLocation(phone: String, location: Location)
    suspend fun savePersonalInfo(phone: String, fullName: String, profileUri: Uri?)
    suspend fun saveWorkShowcase(phone: String, workMedia: List<String>, workDescription: String)
    suspend fun getUserProgress(phone: String): AccountSetupStep
    suspend fun uploadNationalIdImages(phone: String, frontUri: Uri?, backUri: Uri?)
    suspend fun completeUserSetup(phone: String)
    suspend fun getStats(userId: String): Stats?
}