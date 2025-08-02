package com.paris_2.san3a.domain.repository

interface OnboardingRepository {
    suspend fun setOnboardingCompleted()
    suspend fun isOnboardingCompleted(): Boolean
}
