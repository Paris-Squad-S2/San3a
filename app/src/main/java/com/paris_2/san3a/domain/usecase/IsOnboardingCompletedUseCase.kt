package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.OnboardingRepository

class IsOnboardingCompletedUseCase(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(): Boolean {
        return onboardingRepository.isOnboardingCompleted()
    }
}