package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.UserRepository

class GetCustomerRatingOnCraftsmanUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(craftsmanId: String, userId: String) =
        userRepository.getCustomerRatingOnCraftsman(craftsmanId, userId)
}