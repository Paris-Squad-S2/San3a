package com.paris_2.san3a.presentation.screen.verification

import android.net.Uri
import android.util.Log
import androidx.annotation.StringRes
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

data class VerificationScreenState(
    val verificationUiState: VerificationUiState = VerificationUiState(),
    val isLoading: Boolean = false,
    @StringRes val successMessageSnackBar: Int? = null,
    @StringRes val errorMessage: Int? = null,
    val showSnackBarSuccess: Boolean = false,
    val showSnackBarError: Boolean = false,
    val isNoInternet: Boolean = false
)

data class VerificationUiState(
    val frontOfNationalIdUri: Uri? = null,
    val backOfNationalIdUri: Uri? = null,
    val phoneNumber: String = "",
    @StringRes val errorMessage: Int? = null,
)

class VerificationViewModel(
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase
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
                isLoading = false,
                isNoInternet = false,
                errorMessage = null,
                verificationUiState = screenState.value.verificationUiState.copy(
                    phoneNumber = phoneNumber
                )
            )
        )
    }

    private fun onGetPhoneNumberError(th: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = R.string.phone_number_not_found,
                isLoading = false,
                isNoInternet = false,
                showSnackBarError = true,
                showSnackBarSuccess = false
            )
        )
    }

    override fun onBackClick() {
        navigateUp()
    }

    override fun onClickSave() {
        Log.d("123a","1e")
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
        uploadNationalIdImages()
    }

    private fun uploadNationalIdImages() {
        Log.d("123a","2e")
        if (screenState.value.verificationUiState.backOfNationalIdUri != null &&
            screenState.value.verificationUiState.frontOfNationalIdUri != null
        ) {
            Log.d("123a","3e")
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
        Log.d("123a","4e")
        updateState(
            screenState.value.copy(
                isLoading = false,
                isNoInternet = false,
                errorMessage = null,
                showSnackBarSuccess = true,
                showSnackBarError = false,
                successMessageSnackBar = R.string.r_string_national_id_images_uploaded_successfully
            )
        )
    }

    private fun onUploadNationalIdImagesError(throwable: Throwable) {
        Log.d("123a","$throwable")
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false,
                    showSnackBarSuccess = false,
                    successMessageSnackBar = null
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    errorMessage = R.string.national_id_images_uploaded_failed,
                    isNoInternet = false,
                    isLoading = false,
                    showSnackBarError = true,
                    showSnackBarSuccess = false,
                    successMessageSnackBar = null
                )
            )
        }
    }

    fun onFrontNationalIdSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                verificationUiState = screenState.value.verificationUiState.copy(
                    frontOfNationalIdUri = uri
                ),
            )
        )
    }

    fun onBackNationalIdSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                verificationUiState = screenState.value.verificationUiState.copy(
                    backOfNationalIdUri = uri

                )
            )
        )
    }
}