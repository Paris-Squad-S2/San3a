package com.paris_2.san3a.presentation.screen.requests.customer

import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersCountUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCustomerRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MyRequestCustomerViewModel(
    private val getCustomerRequestsUseCase: GetCustomerRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getOffersCountUseCase: GetOffersCountUseCase,
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel<MyRequestCustomerScreenState>(MyRequestCustomerScreenState()), MyRequestCustomerInteractionListener {

    init {
        getCustomerPhone()
    }

    private fun getCustomerPhone() {
        tryToExecute(
            execute = {
                screenState.value.copy(
                    isLoading = true,
                )
                getPhoneNumberUseCase()
            },
            onSuccess = { phoneNumber: String ->
                updateState(
                    screenState.value.copy(
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                            customerPhone = phoneNumber
                        )
                    )
                )
                getRequests()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = "Failed to fetch phone number"
                    )
                )
            }
        )
    }

    private fun getRequests() {
        tryToObserve(
            observe = {
                getCustomerRequestsUseCase(screenState.value.myRequestCustomerUiState.customerPhone)
            },
            onEach = { result ->
                val result = result.toRequestServiceUiStateList()
                updateState(
                    MyRequestCustomerScreenState(
                        isLoading = false,
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                            ongoing = result.filter { it.status == RequestStatus.ONGOING },
                            completed = result.filter { it.status == RequestStatus.COMPLETED },
                            canceled = result.filter { it.status == RequestStatus.CANCELLED }
                        )
                    )
                )
            },
            onError = {
                updateState(
                    MyRequestCustomerScreenState(
                        isLoading = false,
                        errorMessage = it.message
                    )
                )
            }
        )
    }

    override fun onRequestClick(requestId: String) {
        navigate(Destinations.RequestDetails(requestId, phoneNumber = screenState.value.myRequestCustomerUiState.customerPhone))
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onRetryClick() {
        getCustomerPhone()
    }
}