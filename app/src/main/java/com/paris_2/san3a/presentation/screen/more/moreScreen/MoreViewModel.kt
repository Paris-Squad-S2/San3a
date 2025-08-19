package com.paris_2.san3a.presentation.screen.more.moreScreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.exceptions.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.user.CustomizeProfileSettingsUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.GetVersionNameUseCase
import com.paris_2.san3a.domain.usecase.user.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.SetUpAccountUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.presentation.LocalAccountType
import com.paris_2.san3a.presentation.mapper.toUserUiState
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoreViewModel(
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getRatingForCraftsmanUseCase: GetRatingForCraftsmanUseCase,
    private val customizeProfileSettingsUseCase: CustomizeProfileSettingsUseCase,
    private val getUnReadNotificationsCountUseCase: GetUnReadNotificationsCountUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getVersionNameUseCase: GetVersionNameUseCase,
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

    private fun onGetVersionNameError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.occrus_error_when_get_version_name,
                showSnackBarError = true,
                isNoInternet = false,
                isLoading = false
            )
        )
        hideSnackBar()
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
        hideSnackBar()
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
            execute = { scope ->
                val craftsManDeferred =
                    scope.async { getUserUseCase(screenState.value.moreUiState.userUiState.phoneNumber) }

                val ratingDeferred = scope.async {
                    getRatingForCraftsmanUseCase(screenState.value.moreUiState.userUiState.phoneNumber)
                }

                craftsManDeferred.await() to ratingDeferred.await()
            },
            onSuccess = { (user, rating) -> onGetUserInformationSuccess(user, rating) },
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
        hideSnackBar()
    }

    private fun onGetUserInformationSuccess(user: User, rating: Flow<Float>) {
        Log.d("MoreViewModel", "User: ${user.profilePhoto}")
        tryToObserve(
            observe = { rating },
            onEach = { userRating ->
                val isVerify = user.nationalIdBackImage.isNotEmpty() &&
                        user.nationalIdFrontImage.isNotEmpty() &&
                        user.accountType == AccountType.CRAFTSMAN
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = null,
                        isLoadingChangeAccount = false,
                        showSnackBarError = false,
                        isNoInternet = false,
                        moreUiState = screenState.value.moreUiState.copy(
                            userUiState = user.toUserUiState(rating = userRating ?: 0.0f)
                                .copy(isVerify = isVerify),
                        )
                    )
                )

            },
            onError = {
                Log.d("MoreViewModel", "Error getting rating: ${it.message}")
            }
        )
    }

    private fun onGetUserInformationError(th: Throwable) {
        if (th is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false,
                    isLoadingChangeAccount = false
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    errorMessage = R.string.user_information_not_found,
                    isNoInternet = false,
                    isLoading = false,
                    showSnackBarError = true,
                    isLoadingChangeAccount = false
                )
            )
            hideSnackBar()
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
        getNotificationsCount(phoneNumber)
    }

    private fun getNotificationsCount(userId: String) {
        tryToObserve(
            observe = {
                getUnReadNotificationsCountUseCase(userId)
            },
            onEach = { count ->
                updateState(
                    screenState.value.copy(
                        moreUiState = screenState.value.moreUiState.copy(
                            notificationsCount = count ?: 0
                        )
                    )
                )
            },
            onError = { exception ->
                Log.e(
                    "MessagesViewModel",
                    "Error fetching notifications count: ${exception.message}"
                )
            },
        )
    }


    private fun onGetPhoneNumberError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.phone_number_not_found,
                showSnackBarError = true,
                showSnackBarSuccess = false
            )
        )
        hideSnackBar()
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
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoadingChangeAccount = false,
                    isLoading = false
                )
            )
        } else {
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
            hideSnackBar()
        }

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
        hideSnackBar()
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
        if ((screenState.value.moreUiState.userUiState.name.isNotEmpty() &&
                    screenState.value.moreUiState.userUiState.name != screenState.value.moreUiState.userUiState.previousText) ||
            (screenState.value.moreUiState.userUiState.imageUrl != null
                    )
        ) {
            updateState(
                screenState.value.copy(
                    moreUiState = screenState.value.moreUiState.copy(
                        userUiState = screenState.value.moreUiState.userUiState.copy(
                            previousText = screenState.value.moreUiState.userUiState.name
                        )
                    )
                )
            )
            saveUserInformation()
        }
    }

    private fun saveUserInformation() {
        updateState(
            screenState.value.copy(
                isLoading = true,
            )
        )
        tryToExecute(
            execute = {
                Log.d("MoreViewModel", "Enter the edit scope...")
                Log.d("MoreViewModel", screenState.value.moreUiState.userUiState.imageUrl.toString())
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
        hideSnackBar()
    }

    private fun onSaveUserInformationError(th: Throwable) {
        if (th is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(isNoInternet = true)
            )
        } else {
            updateState(
                screenState.value.copy(
                    errorMessage = R.string.occrus_error_when_saving_user_information,
                    isLoading = false
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
        hideSnackBar()
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
                successMessageSnackBar = null,
                isLoadingChangeAccount = false
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

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBarError || screenState.value.showSnackBarSuccess) {
                delay(3000)
                updateState(
                    screenState.value.copy(
                        showSnackBarError = false,
                        showSnackBarSuccess = false
                    )
                )
            }
        }
    }


}