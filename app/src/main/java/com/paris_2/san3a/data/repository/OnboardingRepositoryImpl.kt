package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.source.AppPreferences
import com.paris_2.san3a.domain.GetOnboardingCompletedException
import com.paris_2.san3a.domain.SetOnboardingCompletedException
import com.paris_2.san3a.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val appPreferences: AppPreferences
) : OnboardingRepository, BaseRepository() {
    override suspend fun setOnboardingCompleted() {
        safeCall(SetOnboardingCompletedException()) {
            appPreferences.setOnboardingCompleted()
        }
    }

    override suspend fun isOnboardingCompleted(): Boolean {
        return safeCall(GetOnboardingCompletedException()) {
            appPreferences.isOnboardingCompleted()
        }
    }
}