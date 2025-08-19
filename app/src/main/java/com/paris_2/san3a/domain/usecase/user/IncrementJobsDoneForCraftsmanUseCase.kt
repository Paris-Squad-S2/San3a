package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class IncrementJobsDoneForCraftsmanUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(craftsmanId: String, requestId: String, userId: String) =
        userRepository.incrementJobsDoneForCraftsman(
            craftsmanId = craftsmanId,
            requestId = requestId,
            userId = userId
        )
}