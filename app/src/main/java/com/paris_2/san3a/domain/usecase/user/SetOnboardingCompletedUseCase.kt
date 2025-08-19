package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserPreferencesRepository

class SetOnboardingCompletedUseCase(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend operator fun invoke() = userPreferencesRepository.setOnboardingCompleted()
}