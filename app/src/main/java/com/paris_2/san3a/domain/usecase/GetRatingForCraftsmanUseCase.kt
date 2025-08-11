package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.UserRepository

class GetRatingForCraftsmanUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(craftsmanId: String) =
        userRepository.getRatingForCraftsman(craftsmanId)
}