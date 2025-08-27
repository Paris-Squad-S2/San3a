package com.paris_2.san3a.presentation.screen.verification

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.exceptions.NoInternetConnectionException
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.SetUpAccountUseCase
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.shared.utils.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class VerificationScreenState(
    val verificationUiState: VerificationUiState = VerificationUiState(),
    val isLoading: Boolean = true,
    val successMessageSnackBar: UiText? = null,
    val errorMessage: UiText? = null,
    val showSnackBarSuccess: Boolean = false,
    val showSnackBarError: Boolean = false,
    val isNoInternet: Boolean = false,
    val verificationButtonState: AppButtonState = AppButtonState.Disabled,
)

data class VerificationUiState(
    val frontOfNationalIdUri: Uri? = null,
    val backOfNationalIdUri: Uri? = null,
    val phoneNumber: String = "",
    val errorMessage: UiText? = null,
)

class VerificationViewModel(
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel<VerificationScreenState>(VerificationScreenState()),
    VerificationInteractionListener {

    init {
        getPhoneNumber()
    }

    private fun getPhoneNumber() {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = ::onGetPhoneNumberSuccess,
            onError = ::onGetPhoneNumberError
        )
    }

    private fun onGetPhoneNumberSuccess(phoneNumber: String) {
        updateState(
            screenState.value.copy(
                verificationUiState = screenState.value.verificationUiState.copy(
                    phoneNumber = phoneNumber
                )
            )
        )
        getCurrentUser()
    }

    private fun getCurrentUser() {
        tryToExecute(
            execute = { getUserUseCase(screenState.value.verificationUiState.phoneNumber) },
            onSuccess = { user ->
                updateState(
                    screenState.value.copy(
                        verificationUiState = screenState.value.verificationUiState.copy(
                            frontOfNationalIdUri = if (user.nationalIdFrontImage.isNotBlank()) user.nationalIdFrontImage.toUri() else null,
                            backOfNationalIdUri = if (user.nationalIdBackImage.isNotBlank()) user.nationalIdBackImage.toUri() else null
                        ),
                        isLoading = false,
                    )
                )
            },
            onError = {
                if (it is NoInternetConnectionException) {
                    updateState(
                        screenState.value.copy(
                            isNoInternet = true,
                            isLoading = false,
                            showSnackBarError = false,
                            showSnackBarSuccess = false,
                            successMessageSnackBar = null
                        )
                    )
                } else {
                    updateState(
                        screenState.value.copy(
                            isLoading = false,
                            errorMessage = UiText.StringResource(R.string.user_information_not_found),
                            showSnackBarError = true,
                            showSnackBarSuccess = false
                        )
                    )
                    hideSnackBar()
                }
            }
        )
    }

    private fun onGetPhoneNumberError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = UiText.StringResource(R.string.phone_number_not_found),
                isLoading = false,
                isNoInternet = false,
                showSnackBarError = true,
                showSnackBarSuccess = false
            )
        )
        hideSnackBar()
    }

    override fun onBackClick() {
        navigateUp()
    }

    override fun onClickSave() {
        uploadNationalIdImages()
    }

    override fun onClickRetry() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                isNoInternet = false,
                errorMessage = null,
                showSnackBarError = false,
                showSnackBarSuccess = false,
                successMessageSnackBar = null
            )
        )
        getPhoneNumber()
    }

    private fun uploadNationalIdImages() {
        if (screenState.value.verificationUiState.backOfNationalIdUri != null &&
            screenState.value.verificationUiState.frontOfNationalIdUri != null
        ) {

            updateState(
                screenState.value.copy(
                    verificationButtonState = AppButtonState.Loading
                )
            )
            tryToExecute(
                execute = {

                    setUpAccountUseCase.uploadNationalIdImages(
                        phone = screenState.value.verificationUiState.phoneNumber,
                        frontUri = screenState.value.verificationUiState.frontOfNationalIdUri,
                        backUri = screenState.value.verificationUiState.backOfNationalIdUri
                    )
                },
                onSuccess = ::onUploadNationalIdImagesSuccess,
                onError = ::onUploadNationalIdImagesError
            )
        }
    }

    private fun onUploadNationalIdImagesSuccess(unit: Unit) {

        updateState(
            screenState.value.copy(
                isLoading = false,
                isNoInternet = false,
                errorMessage = null,
                showSnackBarSuccess = true,
                showSnackBarError = false,
                verificationButtonState = AppButtonState.Enable,
                successMessageSnackBar = UiText.StringResource(R.string.r_string_national_id_images_uploaded_successfully)
            )
        )
        hideSnackBar()
        navigateUp()
    }

    private fun onUploadNationalIdImagesError(throwable: Throwable) {

        if (throwable is NoInternetConnectionException) {

            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false,
                    showSnackBarSuccess = false,
                    successMessageSnackBar = null,
                    verificationButtonState = AppButtonState.Enable,
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    errorMessage = UiText.StringResource(R.string.national_id_images_uploaded_failed),
                    isNoInternet = false,
                    isLoading = false,
                    showSnackBarError = true,
                    showSnackBarSuccess = false,
                    successMessageSnackBar = null,
                    verificationButtonState = AppButtonState.Disabled,
                )
            )
            hideSnackBar()
        }
    }

    fun onFrontNationalIdSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                verificationUiState = screenState.value.verificationUiState.copy(
                    frontOfNationalIdUri = uri
                ),
                verificationButtonState = if (screenState.value.verificationUiState.backOfNationalIdUri != null)
                    AppButtonState.Enable
                else
                    AppButtonState.Disabled
            )
        )
    }

    fun onBackNationalIdSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                verificationUiState = screenState.value.verificationUiState.copy(
                    backOfNationalIdUri = uri,
                ),
                verificationButtonState = if (screenState.value.verificationUiState.frontOfNationalIdUri != null)
                    AppButtonState.Enable
                else
                    AppButtonState.Disabled
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