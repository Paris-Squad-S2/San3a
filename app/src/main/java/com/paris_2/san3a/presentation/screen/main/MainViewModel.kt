package com.paris_2.san3a.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.usecase.CustomizeProfileSettingsUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel (
    private val customizeProfileSettings: CustomizeProfileSettingsUseCase,
) : BaseViewModel<MainUiState>(MainUiState()) {

    init {
        viewModelScope.launch {
            updateState(
                screenState.value.copy(
                    isDark = customizeProfileSettings.isDarkThemeEnabled()
                )
            )
        }
    }

}