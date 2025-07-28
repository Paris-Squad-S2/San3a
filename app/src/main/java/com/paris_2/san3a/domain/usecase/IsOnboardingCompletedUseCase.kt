package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.data.source.AppPreferences

class IsOnboardingCompletedUseCase(
    private val appPreferences: AppPreferences
) {
    operator fun invoke(): Boolean {
        return appPreferences.isOnboardingCompleted()
    }
}