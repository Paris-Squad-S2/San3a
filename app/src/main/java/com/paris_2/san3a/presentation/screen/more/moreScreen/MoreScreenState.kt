package com.paris_2.san3a.presentation.screen.more.moreScreen

import android.net.Uri
import androidx.annotation.StringRes
import com.paris_2.san3a.presentation.shared.utils.UiText

data class MoreScreenState(
    val moreUiState: MoreUiState = MoreUiState(),
    val showEditProfileBottomSheet: Boolean = false,
    val showLanguageBottomSheet: Boolean = false,
    val errorMessage: UiText? = null,
    val successMessageSnackBar: UiText? = null,
    val isNoInternet: Boolean = false,
    val isLoading: Boolean = true,
    val showSnackBarSuccess: Boolean = false,
    val showSnackBarError: Boolean = false,
    val isLoadingChangeAccount: Boolean = false,
    val showLogoutBottomSheet: Boolean = false,
)

data class MoreUiState(
    val userUiState: UserUiState = UserUiState(),
    val editUiState: EditProfileUiState = EditProfileUiState(),
    val notificationsCount: Int = 0,
    val isDarkMode: Boolean = false,
    val versionNumber: String = "",
    val selectedLanguage: String = LanguageUiState.ENGLISH.name,
)

enum class LanguageUiState(name: String) {
    ENGLISH("en"),
    ARABIC("ar")
}

data class UserUiState(
    val imageUrl: Uri? = null,
    val name: String = "",
    val rating: Float = 0.0f,
    val phoneNumber: String = "",
    val isVerify: Boolean = false,
    val isCraftsman: Boolean = true,
    val previousImage: Uri? = null,
    val previousText: String = "",
)

data class EditProfileUiState(
    val name: String = "",
    val imageUrl: Uri? = null,
)