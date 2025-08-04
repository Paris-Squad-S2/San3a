package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.ProfileRepository

class CustomizeProfileSettingsUseCase (
    private val profileRepository: ProfileRepository
) {
    suspend fun updateAppLanguage(newLanguage: String) {
       profileRepository.updateAppLanguage(newLanguage)
    }

     suspend fun getLatestSelectedAppLanguage() =
        profileRepository.getLatestSelectedAppLanguage()

    suspend fun setAppThemeToDark(isDarkTheme: Boolean) {
        profileRepository.setDarkTheme(isDarkTheme)
    }

    suspend fun isDarkThemeEnabled() = profileRepository.isDarkThemeEnabled()

}