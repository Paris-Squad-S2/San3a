package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.domain.FailException
import com.paris_2.san3a.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val localDataStoreImpl: LocalDataStore
) : OnboardingRepository, BaseRepository() {
    override suspend fun setOnboardingCompleted() {
        safeCall(FailException("Failed to set onboarding completed")) {
            localDataStoreImpl.setOnboardingCompleted()
        }
    }

    override suspend fun isOnboardingCompleted(): Boolean {
        return safeCall(FailException("Failed to check if onboarding is completed")) {
            localDataStoreImpl.isOnboardingCompleted()
        }
    }
}