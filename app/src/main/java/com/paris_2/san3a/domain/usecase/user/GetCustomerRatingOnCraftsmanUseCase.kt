package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class GetCustomerRatingOnCraftsmanUseCase(private val userRepository: UserRepository) {
    operator fun invoke(craftsmanId: String, offerId: String, userId: String) =
        userRepository.getCustomerRatingOnCraftsman(
            craftsmanId = craftsmanId,
            offerId = offerId,
            userId = userId
        )
}