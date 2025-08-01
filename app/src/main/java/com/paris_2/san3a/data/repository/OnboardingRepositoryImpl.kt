package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.source.AppPreferences
import com.paris_2.san3a.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val appPreferences: AppPreferences
): OnboardingRepository {
    override fun setOnboardingCompleted() {
        appPreferences.setOnboardingCompleted()
    }

    override fun isOnboardingCompleted(): Boolean {
        return appPreferences.isOnboardingCompleted()
    }
}