package com.paris_2.san3a.presentation.screen.more

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.CustomizeProfileSettingsUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SetLoginUseCase
import com.paris_2.san3a.presentation.mapper.toUserUiState
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class MoreScreenState(
    val moreUiState: MoreUiState = MoreUiState(),
    val showEditProfileBottomSheet: Boolean = false,
    val showLanguageBottomSheet: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

data class MoreUiState(
    val userUiState: UserUiState = UserUiState(),
    val isDarkMode: Boolean = false,
    val versionNumber: String = "0.0.0",
    val selectedLanguage: String = LanguageUiState.ENGLISH.name
)

enum class LanguageUiState(name: String) {
    ENGLISH("en"),
    ARABIC("ar")
}

data class UserUiState(
    val imageUrl: String = "",
    val name: String = "",
    val review: Int = 0,
    val rating: Double = 0.0,
    val phoneNumber: String = "",
    val isVerify: Boolean = false,
    val isCraftsman: Boolean = false,
)

class MoreViewModel(
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val setLoginUseCase: SetLoginUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val customizeProfileSettingsUseCase: CustomizeProfileSettingsUseCase
) : BaseViewModel<MoreScreenState>(MoreScreenState()), MoreInteractionListener {

    init {
        getDarkMode()
        getLanguageSelected()
        updatePhoneNumber()
        getUserInformation()
    }
    
    private fun getLanguageSelected(){
        tryToExecute(
            execute = {customizeProfileSettingsUseCase.getLatestSelectedAppLanguage()},
            onSuccess = ::onGetLanguageSelectedSuccess,
            onError = ::onGetLanguageSelectedError
        )
    }
    
    private suspend fun onGetLanguageSelectedSuccess(selectedLanguage: Flow<String>){
        selectedLanguage.collect { languageSelected ->
            updateState(screenState.value.copy(
                moreUiState = screenState.value.moreUiState.copy(
                    selectedLanguage = languageSelected
                )
            ))
        }
    }
    
    private fun onGetLanguageSelectedError(th: Throwable){
        updateState(
            screenState.value.copy(
                errorMessage = R.string.occrus_error_when_get_languag_selected
            )
        )
    }

    private fun updatePhoneNumber() {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = ::onGetPhoneNumberSuccess,
            onError = ::onGetPhoneNumberError
        )
    }

    private fun getUserInformation() {
        tryToExecute(
            execute = { getUserUseCase(screenState.value.moreUiState.userUiState.phoneNumber) },
            onSuccess = ::onGetUserInformationSuccess,
            onError = ::onGetUserInformationError
        )
    }

    private fun getDarkMode() {
        tryToExecute(
            execute = { customizeProfileSettingsUseCase.isDarkThemeEnabled() },
            onSuccess = ::onGetDarkModeSuccess,
            onError = ::onGetDarkModeError
        )
    }

    private fun onGetDarkModeSuccess(isDarkMode: Flow<Boolean>) {
        viewModelScope.launch {
            isDarkMode.collectLatest {
                updateState(
                    screenState.value.copy(
                        moreUiState = screenState.value.moreUiState.copy(
                            isDarkMode = it
                        )
                    )
                )
            }
        }

    }

    private fun onGetDarkModeError(th: Throwable) {

    }

    private fun onGetUserInformationSuccess(user: User) {
        updateState(
            screenState.value.copy(
                moreUiState = screenState.value.moreUiState.copy(
                    userUiState = user.toUserUiState(),
                )
            )
        )
    }

    private fun onGetUserInformationError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.user_information_not_found
            )
        )
    }

    private fun onGetPhoneNumberSuccess(phoneNumber: String) {
        updateState(
            screenState.value.copy(
                screenState.value.moreUiState.copy(
                    userUiState = screenState.value.moreUiState.userUiState.copy(
                        phoneNumber = phoneNumber
                    )
                )
            )
        )
    }

    private fun onGetPhoneNumberError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.phone_number_not_found
            )
        )
    }

    override fun onClickEditProfileBottomSheet() {
        updateState(
            screenState.value.copy(
                showEditProfileBottomSheet = !screenState.value.showEditProfileBottomSheet
            )
        )
    }

    override fun onClickSwitchAccountToCraftsman() {
        TODO("Not yet implemented")
    }

    override fun onClickSwitchAccountToCustomer() {
        TODO("Not yet implemented")
    }

    override fun onClickLanguage() {
        updateState(
            screenState.value.copy(
                showLanguageBottomSheet = !screenState.value.showLanguageBottomSheet
            )
        )
    }

    override fun onClickLocation() {
        TODO("Not yet implemented")
    }

    override fun onClickLogout() {
        tryToExecute(
            execute = {
                savePhoneNumberUseCase("")
                setLoginUseCase(false)
            },
            onSuccess = ::onLogoutSuccess,
            onError = ::onLogoutError
        )
    }

    private fun onLogoutSuccess(unit: Unit) {
        navigate(Destinations.RegisterScreen)
    }

    private fun onLogoutError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.logout_failed
            )
        )
    }

    override fun onChangeToDarkMode(isDarkMode: Boolean) {
        updateState(
            screenState.value.copy(
                screenState.value.moreUiState.copy(
                    isDarkMode = isDarkMode,
                )
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            customizeProfileSettingsUseCase.setAppThemeToDark(isDarkMode)
        }
    }


    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onClickBecomeACraftsman() {
        TODO("Not yet implemented")
    }

    override fun onNameValueChange(name: String) {
        updateState(
            screenState.value.copy(
                moreUiState = screenState.value.moreUiState.copy(
                    userUiState = screenState.value.moreUiState.userUiState.copy(
                        name = name
                    )
                )
            )
        )
    }

    override fun onCloseEditProfileBottomSheet() {
        updateState(
            screenState.value.copy(showEditProfileBottomSheet = false)
        )
    }

    override fun onCloseSelectedLanguageBottomSheet() {
        updateState(
            screenState.value.copy(showLanguageBottomSheet = false)
        )
    }

    override fun onLanguageSelected(language: String) {
        updateState(
            screenState.value.copy(
                moreUiState = screenState.value.moreUiState.copy(
                    selectedLanguage = language
                )
            )
        )

        tryToExecute(
            execute = {
                customizeProfileSettingsUseCase.updateAppLanguage(screenState.value.moreUiState.selectedLanguage)
            },
            onError = ::onUppAppLanguageError
        )

        updateState(
            screenState.value.copy(showLanguageBottomSheet = false)
        )

    }
    
    
    private fun onUppAppLanguageError(th: Throwable){
        updateState(
            screenState.value.copy(errorMessage = R.string.occruc_error_when_language_app_updated)
        )
    }

    override fun onPickImageClick() {
        TODO("Not yet implemented")
    }

}