package com.paris_2.san3a.domain.repository

interface OnboardingRepository {
    fun setOnboardingCompleted()
    fun isOnboardingCompleted(): Boolean
}
