package com.paris_2.san3a.presentation.screen.splash

import androidx.navigation.NavOptions
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.user.SetUpAccountUseCase
import com.paris_2.san3a.presentation.LocalAccountType
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class SplashScreenViewModel(
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
) : BaseViewModel<SplashScreenUiState>(SplashScreenUiState()) {

    init {
        fetchUserProgress()
    }

    fun fetchUserProgress() {
        tryToExecute(
            execute = {
                updateState(
                    newState = screenState.value.copy(
                        isLoading = true,
                        error = null
                    )
                )
               val phoneNumber = getPhoneNumberUseCase.invoke()
                    .let { phoneNumber ->
                        if (phoneNumber.isBlank()) {
                            handleNewUserDestination()
                        }
                        setUpAccountUseCase.getUserProgress(phoneNumber)
                    }
                phoneNumber
            },
            onSuccess = { progress ->
                when (progress) {
                    AccountSetupStep.ACCOUNT_TYPE -> handleAccountTypeDestination()
                    AccountSetupStep.COMPLETED -> handleAccountCompletedDestination()
                    else -> handleAccountProgressDestination(progress)
                }
                updateState(
                    newState = screenState.value.copy(
                        isLoading = false,
                        error = null
                    )
                )
            },
            onError = ::handleError
        )
    }

    private fun handleNewUserDestination() {
        tryToExecute(
            execute = { isOnboardingCompletedUseCase() },
            onSuccess = {
                updateState(
                    newState = screenState.value.copy(
                        destination = if (it) {
                            Destinations.RegisterScreen
                        } else {
                            Destinations.OnBoarding
                        }
                    )
                )
            },
            onError = ::handleError
        )
    }

    private fun handleAccountProgressDestination(progress: AccountSetupStep) {
        tryToExecute(
            execute = {
                updateState(
                    newState = screenState.value.copy(
                        destination = Destinations.Account(progress)
                    )
                )
            },
            onError = ::handleError
        )
    }

    private fun handleAccountCompletedDestination() {
        tryToExecute(
            execute = {
                getUserUseCase(getPhoneNumberUseCase())
            },
            onSuccess = { user ->
                when (user.accountType) {
                    AccountType.CUSTOMER -> {
                        LocalAccountType.value = AccountType.CUSTOMER
                        updateState(
                            newState = screenState.value.copy(
                                destination = Destinations.CustomerGraph
                            )
                        )
                    }

                    AccountType.CRAFTSMAN -> {
                        LocalAccountType.value = AccountType.CRAFTSMAN
                        updateState(
                            newState = screenState.value.copy(
                                destination = Destinations.CraftManGraph
                            )
                        )
                    }
                }
            },
            onError = ::handleError
        )
    }

    private fun handleAccountTypeDestination() {
        tryToExecute(
            execute = {
                isOnboardingCompletedUseCase()
            },
            onSuccess = { isCompleted ->
                updateState(
                    newState = screenState.value.copy(
                        error = null,
                        destination = if (isCompleted) {
                            Destinations.RegisterScreen
                        } else {
                            Destinations.OnBoarding
                        }
                    )
                )
            },
            onError = ::handleError
        )
    }

    fun navigateToDestination() {
        tryToExecute(
            execute = {
                updateState(
                    newState = screenState.value.copy(
                        isLoading = false
                    )
                )
                if (screenState.value.destination != null) {
                    navigate(
                        screenState.value.destination ?: Destinations.OnBoarding,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(Destinations.Splash, inclusive = true)
                            .build()
                    )
                }
            },
            onError = ::handleError
        )
    }

    private fun handleError(error: Throwable) {
        updateState(
            newState = screenState.value.copy(
                error = error.message,
                isLoading = false
            )
        )

    }

    fun onRetryClicked() {
        fetchUserProgress()
        navigateToDestination()
    }
}
