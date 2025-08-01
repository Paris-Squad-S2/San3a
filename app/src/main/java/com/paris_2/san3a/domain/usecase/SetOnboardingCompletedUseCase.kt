package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.OnboardingRepository

class SetOnboardingCompletedUseCase(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke() {
        onboardingRepository.setOnboardingCompleted()
    }
}