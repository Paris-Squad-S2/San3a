package com.paris_2.san3a.presentation.screen.more

import androidx.annotation.StringRes
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

data class MoreScreenState(
    val moreUiState: MoreUiState = MoreUiState(),
    @StringRes val errorMessage: Int? = null
)

data class MoreUiState(
    val imageUrl: String = "",
    val name: String = "",
    val review: Int = 0,
    val rating: Double = 0.0,
    val phoneNumber: String = "",
    val isDarkMode: Boolean = false,
    val isVerify: Boolean = false,
    val isCraftsman: Boolean = false,
    val versionNumber: String = "0.0.0"
)

class MoreViewModel(
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase
) : BaseViewModel<MoreScreenState>(MoreScreenState()), MoreInteractionListener {

    init {
        updatePhoneNumber()
    }

    private fun updatePhoneNumber() {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = ::onGetPhoneNumberSuccess,
            onError = ::onGetPhoneNumberError
        )
    }

    private fun onGetPhoneNumberSuccess(phoneNumber: String) {
        updateState(
            screenState.value.copy(
                screenState.value.moreUiState.copy(
                    phoneNumber = phoneNumber
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

    override fun onClickEdit() {
        TODO("Not yet implemented")
    }

    override fun onClickSwitchAccountToCraftsman() {
        TODO("Not yet implemented")
    }

    override fun onClickSwitchAccountToCustomer() {
        TODO("Not yet implemented")
    }

    override fun onClickLanguage() {
        TODO("Not yet implemented")
    }

    override fun onClickLocation() {
        TODO("Not yet implemented")
    }

    override fun onClickLogout() {
        TODO("Not yet implemented")
    }

    override fun onChangeToDarkMode(isDarkMode: Boolean) {
        updateState(
            screenState.value.copy(
                screenState.value.moreUiState.copy(
                    isDarkMode = isDarkMode,
                    isCraftsman = isDarkMode
                )
            )
        )
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onClickBecomeACraftsman() {
        TODO("Not yet implemented")
    }


}