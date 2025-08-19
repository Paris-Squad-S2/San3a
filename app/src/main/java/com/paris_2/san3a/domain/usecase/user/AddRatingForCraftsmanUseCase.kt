package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class AddRatingForCraftsmanUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, craftsmanId: String, rating: Float) =
        userRepository.addRatingForCraftsman(userId, craftsmanId, rating)
}