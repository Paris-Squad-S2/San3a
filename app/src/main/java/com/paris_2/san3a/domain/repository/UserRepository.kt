package com.paris_2.san3a.domain.repository

import android.net.Uri
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Stats
import kotlinx.coroutines.flow.Flow
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User

interface UserRepository {
    suspend fun saveAccountType(phone: String, accountType: AccountType)
    suspend fun getAccountType(phone: String) : AccountType
    suspend fun saveServices(phone: String, services:  List<Service>, isCraftsman: Boolean)
    suspend fun getServices(phone: String) : List<Service>
    suspend fun saveLocation(phone: String, location: Location)
    suspend fun savePersonalInfo(phone: String, fullName: String, profileUri: Uri?)
    suspend fun saveWorkShowcase(phone: String, workMedia: List<Uri>?, workDescription: String)
    suspend fun getUserProgress(phone: String): AccountSetupStep
    suspend fun uploadNationalIdImages(phone: String, frontUri: Uri?, backUri: Uri?)
    suspend fun completeUserSetup(phone: String)
    suspend fun getUser(phone: String): User
    suspend fun getWorkMedia(phone: String): List<String>
    suspend fun getStats(userId: String): Stats?
    fun getRecentRelatedJobs(relatedJob: String): Flow<List<RequestService>>
}