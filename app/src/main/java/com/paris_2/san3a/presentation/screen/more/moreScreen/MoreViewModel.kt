package com.paris_2.san3a.presentation.screen.more.moreScreen

import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import coil3.toUri
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.CustomizeProfileSettingsUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.GetVersionNameUseCase
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SetLoginUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.LocalAccountType
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
    @StringRes val errorMessage: Int? = null,
    @StringRes val successMessageSnackBar: Int? = null,
    val isNoInternet: Boolean = false,
    val isLoading: Boolean = false,
    val showSnackBarSuccess: Boolean = false,
    val showSnackBarError: Boolean = false,
    val isLoadingChangeAccount: Boolean = false,
    val showLogoutBottomSheet: Boolean = false,
)

data class MoreUiState(
    val userUiState: UserUiState = UserUiState(),
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
    val review: Int = 0,
    val rating: Double = 0.0,
    val phoneNumber: String = "",
    val isVerify: Boolean = false,
    val isCraftsman: Boolean = true,
    val previousImage: Uri? = null,
)

class MoreViewModel(
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val setLoginUseCase: SetLoginUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val customizeProfileSettingsUseCase: CustomizeProfileSettingsUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getVersionNameUseCase: GetVersionNameUseCase
) : BaseViewModel<MoreScreenState>(MoreScreenState()), MoreInteractionListener {

    init {
        fetchData()
    }


    private fun fetchData() {
        getVersionName()
        getPhoneNumber()
        getDarkMode()
        getLanguageSelected()
    }

    private fun getVersionName() {
        tryToExecute(
            execute = { getVersionNameUseCase() },
            onSuccess = ::onGetVersionNameSuccess,
            onError = ::onGetVersionNameError
        )
    }
    
    private fun onGetVersionNameSuccess(versionName: String) {
        updateState(
            screenState.value.copy(
                moreUiState = screenState.value.moreUiState.copy(
                    versionNumber = versionName
                )
            )
        )
    }
    
    private fun onGetVersionNameError(throwable: Throwable){
        updateState(
            screenState.value.copy(
                errorMessage = R.string.occrus_error_when_get_version_name,
                showSnackBarError = true,
                isNoInternet = false,
                isLoading = false
            )
        )
    }

    private fun getLanguageSelected() {
        tryToExecute(
            execute = { customizeProfileSettingsUseCase.getLatestSelectedAppLanguage() },
            onSuccess = ::onGetLanguageSelectedSuccess,
            onError = ::onGetLanguageSelectedError
        )
    }

    private suspend fun onGetLanguageSelectedSuccess(selectedLanguage: Flow<String>) {
        selectedLanguage.collect { languageSelected ->
            updateState(
                screenState.value.copy(
                    errorMessage = null,
                    showSnackBarError = false,
                    moreUiState = screenState.value.moreUiState.copy(
                        selectedLanguage = languageSelected
                    )
                )
            )
        }
    }

    private fun onGetLanguageSelectedError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.occrus_error_when_get_languag_selected,
                showSnackBarError = true,
            )
        )
    }

    private fun getPhoneNumber() {
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
                        errorMessage = null,
                        showSnackBarError = false,
                        moreUiState = screenState.value.moreUiState.copy(
                            isDarkMode = it
                        )
                    )
                )
            }
        }

    }

    private fun onGetDarkModeError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.occrus_error_when_get_dark_mode,
                showSnackBarError = true,
            )
        )
    }

    private fun onGetUserInformationSuccess(user: User) {
        val isVerify = user.nationalIdBackImage.isNotEmpty() &&
                user.nationalIdFrontImage.isNotEmpty() &&
                user.accountType == AccountType.CRAFTSMAN
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = null,
                showSnackBarError = false,
                isNoInternet = false,
                moreUiState = screenState.value.moreUiState.copy(
                    userUiState = user.toUserUiState()
                        .copy(isVerify = isVerify),
                )
            )
        )
    }

    private fun onGetUserInformationError(th: Throwable) {
        if (th is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    errorMessage = R.string.user_information_not_found,
                    isNoInternet = false,
                    isLoading = false,
                    showSnackBarError = true
                )
            )
        }

    }

    private fun onGetPhoneNumberSuccess(phoneNumber: String) {
        updateState(
            screenState.value.copy(
                errorMessage = null,
                showSnackBarError = false,
                moreUiState = screenState.value.moreUiState.copy(
                    userUiState = screenState.value.moreUiState.userUiState.copy(
                        phoneNumber = phoneNumber
                    )
                )
            )
        )
        getUserInformation()
    }

    private fun onGetPhoneNumberError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.phone_number_not_found,
                showSnackBarError = true,
                showSnackBarSuccess = false
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
        switchToCraftsmanAccount()
    }

    private fun switchToCraftsmanAccount() {
        updateState(
            screenState.value.copy(
                isLoadingChangeAccount = true,
                isLoading = false,
                errorMessage = null,
                showSnackBarError = false,
                isNoInternet = false,
                showSnackBarSuccess = false

            )
        )
        tryToExecute(
            execute = {
                setUpAccountUseCase.saveAccountType(
                    phone = screenState.value.moreUiState.userUiState.phoneNumber,
                    accountType = AccountType.CRAFTSMAN
                )
            },
            onError = ::onSaveAccountTypeError,
            onSuccess = ::onSaveAccountTypeAsCraftsmanSuccess

        )
    }

    private fun onSaveAccountTypeAsCraftsmanSuccess(unit: Unit) {

        updateState(
            screenState.value.copy(
                isLoadingChangeAccount = false,
                isLoading = false,
                errorMessage = null,
                isNoInternet = false,
                showSnackBarError = false,
                showSnackBarSuccess = false,
                moreUiState = screenState.value.moreUiState.copy(
                    userUiState = screenState.value.moreUiState.userUiState.copy(
                        isCraftsman = true
                    )
                )
            )
        )

        LocalAccountType.value = AccountType.CRAFTSMAN
        navigate(
            destination = Destinations.CraftManGraph
        )
    }


    override fun onClickSwitchAccountToCustomer() {

        updateState(
            screenState.value.copy(
                isLoadingChangeAccount = true,
                isLoading = false,
                errorMessage = null,
                showSnackBarError = false,
                isNoInternet = false,
                showSnackBarSuccess = false

            )
        )
        tryToExecute(
            execute = {
                setUpAccountUseCase.saveAccountType(
                    phone = screenState.value.moreUiState.userUiState.phoneNumber,
                    accountType = AccountType.CUSTOMER
                )
            },
            onError = ::onSaveAccountTypeError,
            onSuccess = ::onSaveAccountTypeAsCustomerSuccess
        )
    }

    private fun onSaveAccountTypeAsCustomerSuccess(unit: Unit) {

        updateState(
            screenState.value.copy(
                isLoadingChangeAccount = false,
                isLoading = false,
                errorMessage = null,
                isNoInternet = false,
                showSnackBarError = false,
                showSnackBarSuccess = false,
                moreUiState = screenState.value.moreUiState.copy(
                    userUiState = screenState.value.moreUiState.userUiState.copy(
                        isCraftsman = false
                    )
                )
            )
        )

        LocalAccountType.value = AccountType.CUSTOMER

        navigate(
            destination = Destinations.CustomerGraph,
        )
    }

    private fun onSaveAccountTypeError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                isLoadingChangeAccount = false,
                errorMessage = R.string.occur_error_when_save_account_type,
                showSnackBarError = true,
                isNoInternet = false,
                isLoading = false,
                showSnackBarSuccess = false
            )
        )
    }


    override fun onClickLanguage() {
        updateState(
            screenState.value.copy(
                showLanguageBottomSheet = !screenState.value.showLanguageBottomSheet
            )
        )
    }

    override fun onClickLocation() {
        navigate(Destinations.Location)
    }

    override fun onClickLogout() {
        updateState(screenState.value.copy(showLogoutBottomSheet = false))
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
                errorMessage = R.string.logout_failed,
                isNoInternet = false,
                isLoading = false,
                showSnackBarError = true,
                showSnackBarSuccess = false
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
        navigate(Destinations.Notification)
    }

    override fun onClickBecomeACraftsman() {
        switchToCraftsmanAccount()
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
        if (screenState.value.moreUiState.userUiState.name.isNotEmpty() ||
            screenState.value.moreUiState.userUiState.imageUrl != null ||
            screenState.value.moreUiState.userUiState.imageUrl != screenState.value.moreUiState.userUiState.previousImage
        ) {
            saveUserInformation()
        }
    }

    private fun saveUserInformation() {
        tryToExecute(
            execute = {
                setUpAccountUseCase.savePersonalInfo(
                    phone = screenState.value.moreUiState.userUiState.phoneNumber,
                    fullName = screenState.value.moreUiState.userUiState.name,
                    profileUri = screenState.value.moreUiState.userUiState.imageUrl
                )
            },
            onSuccess = ::onSaveUserInformationSuccess,
            onError = ::onSaveUserInformationError
        )
    }

    private fun onSaveUserInformationSuccess(unit: Unit) {
        updateState(
            screenState.value.copy(
                showSnackBarSuccess = true,
                successMessageSnackBar = R.string.occrus_user_information_saved_successfully,
                isLoading = false,
                isNoInternet = false,
                showSnackBarError = false
            )
        )
    }

    private fun onSaveUserInformationError(th: Throwable) {
        if (th is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(isNoInternet = true)
            )
        } else {
            updateState(
                screenState.value.copy(
                    errorMessage = R.string.occrus_error_when_saving_user_information
                )
            )
        }

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
            onError = ::onUpdateAppLanguageError
        )

        updateState(
            screenState.value.copy(showLanguageBottomSheet = false)
        )

    }


    private fun onUpdateAppLanguageError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.occruc_error_when_language_app_updated,
                showSnackBarError = true,
                showSnackBarSuccess = false,
                isLoading = false,
                isNoInternet = false
            )
        )
    }

    fun onPickImageClick(uri: Uri) {
        updateState(
            screenState.value.copy(
                moreUiState = screenState.value.moreUiState.copy(
                    userUiState = screenState.value.moreUiState.userUiState.copy(
                        imageUrl = uri
                    )
                )
            )
        )
    }

    override fun onClickRetry() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null,
                isNoInternet = false,
                showSnackBarError = false,
                showSnackBarSuccess = false,
                successMessageSnackBar = null
            )
        )

        fetchData()
    }

    override fun onClickVerification() {
        navigate(Destinations.Verification)
    }

    override fun onClickMyService() {
        navigate(
            Destinations.MyService(
                phoneNumber = screenState.value.moreUiState.userUiState.phoneNumber,
                isCraftsman = screenState.value.moreUiState.userUiState.isCraftsman
            )
        )
    }

    override fun onClickLogoutArrow() {
        updateState(
            screenState.value.copy(
                showLogoutBottomSheet = true
            )
        )
    }

    override fun onDismissLogoutBottomSheet() {
        updateState(
            screenState.value.copy(
                showLogoutBottomSheet = false
            )
        )
    }

    override fun onDismissSnackBar() {
        updateState(
            screenState.value.copy(
                showSnackBarError = false,
                showSnackBarSuccess = false,
                errorMessage = null,
                successMessageSnackBar = null
            )
        )
    }


}