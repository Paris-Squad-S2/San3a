package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.UserRepository

class UpdateEarningsForCraftsmanUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: String,
        craftsmanId: String,
        requestId: String,
        earnings: Double
    ) = userRepository.updateEarningsForCraftsman(userId, craftsmanId, requestId, earnings)
}