package com.paris_2.san3a.data.source.remote.user

import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import kotlinx.coroutines.flow.Flow
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User

interface UserRemoteDataSource {

    suspend fun addUser(phone: String)

    suspend fun updateWorkShowcase(phone: String, workMedia: List<String>?, workDescription: String)

    suspend fun updateUserProgress(phone: String, step: AccountSetupStep)

    suspend fun getUserProgress(phone: String): AccountSetupStep

    suspend fun saveAccountType(phone: String, accountType: AccountType)

    suspend fun getAccountType(phone: String) : AccountType

    suspend fun saveServices(phone: String, services: List<String>, isCraftsman: Boolean)

    fun getServices(phone: String, isCraftsman: Boolean): Flow<List<ServiceDto>>

    suspend fun updateLocation(phone: String, location: Location)

    suspend fun updatePersonalInfo(phone: String, fullName: String, profilePhoto: String?)

    suspend fun updateNationalIdImages(phone: String, frontUrl: String?, backUrl: String?)

    suspend fun getUser(phone: String): User

    suspend fun getWorkMedia(phone: String): List<String>

    suspend fun addRatingForCraftsman(
        userId: String,
        craftsmanId: String,
        rating: Float
    )

    fun getRatingForCraftsman(craftsmanId: String): Flow<Float>

    suspend fun getCustomerRatingOnCraftsman(
        craftsmanId: String,
        userId: String
    ): Float?

    suspend fun updateEarningsForCraftsman(
        craftsmanId: String,
        userId: String,
        requestId: String,
        earnings: Double
    )

    fun getEarningsForCraftsman(craftsmanId: String): Flow<Double>

    suspend fun incrementJobsDoneForCraftsman(craftsmanId: String, requestId: String, userId: String)

    fun getJobsDoneForCraftsman(craftsmanId: String): Flow<Int>
}