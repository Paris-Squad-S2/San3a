package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class GetStatsUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userId: String) = userRepository.getStats(userId)
}