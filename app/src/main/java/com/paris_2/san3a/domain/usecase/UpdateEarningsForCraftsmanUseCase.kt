package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.UserRepository

class UpdateEarningsForCraftsmanUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        craftsmanId: String,
        userId: String,
        requestId: String,
        earnings: Double
    ) = userRepository.updateEarningsForCraftsman(craftsmanId = craftsmanId,userId = userId, requestId, earnings)
}