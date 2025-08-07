package com.paris_2.san3a.presentation.screen.myRequest.craftsman

import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requests.GetGetCraftsManRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.myRequest.customer.MyRequestCustomerInteractionListener
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MyOfferCraftsmanViewModel(
    private val getGetCraftsManRequestsUseCase: GetGetCraftsManRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getOffersUseCase : GetOffersUseCase,
    private val createChatUseCase : CreateChatUseCase,
) : BaseViewModel<MyOfferCraftsmanScreenState>(MyOfferCraftsmanScreenState()), MyJobCraftsmanInteractionListener {

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
                getOffers()
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


    private fun getOffers() {
        tryToObserve(
            observe = {
                getGetCraftsManRequestsUseCase(screenState.value.myOffersCraftsmanUiState.customerPhone)
            },
            onEach = { result ->
                val result = result.toMyJobOfferUiStateList()
                updateState(
                    MyOfferCraftsmanScreenState(
                        isLoading = false,
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            ongoing = listOf(MyJobOfferUiState()),
                            completed = result.filter { it.status == RequestStatus.COMPLETED },
                            canceled = result.filter { it.status == RequestStatus.CANCELLED }
                        )
                    )
                )
            },
            onError = {
                updateState(
                    MyOfferCraftsmanScreenState(
                        isLoading = false,
                        errorMessage = it.message
                    )
                )
            }
        )
    }

    override fun onSendAsDone(requestId: String) {
   /*TODO("Not yet implemented")*/
    }

    override fun onSendMessageClick(phoneNumber: String) {
        tryToExecute(
            execute = {
                createChatUseCase(
                    listOf(screenState.value.myOffersCraftsmanUiState.customerPhone,phoneNumber)
                )
            },
            onSuccess = {chatId->
                navigate(Destinations.MessageDetails(
                    chatId = chatId,
                    currentUserId = screenState.value.myOffersCraftsmanUiState.customerPhone,
                    otherUserId = phoneNumber
                ))
            },
            onError = {
                //todo show snack bar
            }
        )
    }

    override fun onViewRequestDetails(requestId: String) {
        /*TODO("Not yet implemented")*/
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onRetryClick() {
    }
}