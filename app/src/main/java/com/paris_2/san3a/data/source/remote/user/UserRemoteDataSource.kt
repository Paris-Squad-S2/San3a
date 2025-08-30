package com.paris_2.san3a.data.source.remote.user

import com.paris_2.san3a.data.source.remote.user.dto.OtpMessageDto
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.OtpDto
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {

    suspend fun sendOtpMessage(message: OtpMessageDto): OtpDto

    suspend fun addUser(phone: String)

    suspend fun updateWorkShowcase(phone: String, workMedia: List<String>?, workDescription: String)

    suspend fun updateUserProgress(phone: String, step: AccountSetupStep)

    suspend fun getUserProgress(phone: String): AccountSetupStep

    suspend fun saveAccountType(phone: String, accountType: AccountType)

    suspend fun saveServices(phone: String, services: List<String>, isCraftsman: Boolean)

    fun getServices(phone: String, isCraftsman: Boolean): Flow<List<ServiceDto>>

    fun getUserSelectedServices(phone: String, isCraftsman: Boolean): Flow<List<ServiceDto>>

    suspend fun updateLocation(phone: String, location: Location)

    suspend fun updatePersonalInfo(phone: String, fullName: String, profilePhoto: String?)

    suspend fun updateNationalIdImages(phone: String, frontUrl: String?, backUrl: String?)

    suspend fun getUser(phone: String): User

    suspend fun getWorkMedia(phone: String): List<String>

    suspend fun addRatingForCraftsman(
        userId: String,
        craftsmanId: String,
        offerId: String,
        rating: Float,
    )

    fun getRatingForCraftsman(craftsmanId: String): Flow<Float>

    fun getCustomerRatingOnCraftsman(
        craftsmanId: String,
        offerId: String,
        userId: String,
    ): Flow<Float?>

    suspend fun updateEarningsForCraftsman(
        craftsmanId: String,
        userId: String,
        requestId: String,
        earnings: Double
    )

    fun getEarningsForCraftsman(craftsmanId: String): Flow<Double>

    suspend fun incrementJobsDoneForCraftsman(
        craftsmanId: String,
        requestId: String,
        userId: String
    )

    fun getJobsDoneForCraftsman(craftsmanId: String): Flow<Int>
}