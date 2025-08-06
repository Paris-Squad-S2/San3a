package com.paris_2.san3a.presentation.screen.myRequest.craftsman

import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCustomerRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.myRequest.customer.MyRequestCustomerInteractionListener
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MyOfferCraftsmanViewModel(
    private val getCustomerRequestsUseCase: GetCustomerRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel<MyOfferCraftsmanScreenState>(MyOfferCraftsmanScreenState()), MyRequestCustomerInteractionListener {

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
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            customerPhone = phoneNumber
                        )
                    )
                )
                //getRequests()
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

/*    private fun getRequests() {
        tryToObserve(
            observe = {
                getCustomerRequestsUseCase(screenState.value.myOffersCraftsmanUiState.customerPhone)
            },
            onEach = { result ->
                val result = result.toRequestServiceUiStateList()
                updateState(
                    MyOfferCraftsmanScreenState(
                        isLoading = false,
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
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
    }*/

    override fun onRequestClick(requestId: String) {
        TODO("Not yet implemented")
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onRetryClick() {
        getCustomerPhone()
    }
}