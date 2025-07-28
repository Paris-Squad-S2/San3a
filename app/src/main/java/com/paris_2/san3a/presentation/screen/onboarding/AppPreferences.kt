package com.paris_2.san3a.presentation.screen.onboarding

import android.content.Context
import androidx.core.content.edit

class AppPreferences(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isOnboardingCompleted(): Boolean = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)

    fun setOnboardingCompleted(completed: Boolean = true) {
        prefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, completed) }
    }
    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}
