package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.UserPreferencesRepository

class CustomizeProfileSettingsUseCase (
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun updateAppLanguage(newLanguage: String) {
       userPreferencesRepository.updateAppLanguage(newLanguage)
    }

      fun getLatestSelectedAppLanguage() =
        userPreferencesRepository.getLatestSelectedAppLanguage()

    suspend fun setAppThemeToDark(isDarkTheme: Boolean) {
        userPreferencesRepository.setDarkTheme(isDarkTheme)
    }

    suspend fun isDarkThemeEnabled() = userPreferencesRepository.isDarkThemeEnabled()

}