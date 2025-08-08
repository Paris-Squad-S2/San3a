package com.paris_2.san3a.presentation.screen.requests.craftsman

import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requests.GetGetCraftsManRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MyOfferCraftsmanViewModel(
    private val getGetCraftsManRequestsUseCase: GetGetCraftsManRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val createChatUseCase: CreateChatUseCase,
) : BaseViewModel<MyOfferCraftsmanScreenState>(MyOfferCraftsmanScreenState()),
    MyJobCraftsmanInteractionListener {

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
                getCraftsManOfferOnRequest()
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

    private fun getCraftsManOfferOnRequest() {
        tryToObserve(
            observe = {
                getGetCraftsManRequestsUseCase(screenState.value.myOffersCraftsmanUiState.customerPhone)
            },
            onEach = { result ->
                val resultt = result.toMyJobOfferUiStateList() //TODO
                updateState(
                    MyOfferCraftsmanScreenState(
                        isLoading = false,
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            requests = result.toMyJobOfferUiStateMap(),
                            ongoing = resultt.filter { it.status == RequestStatus.ONGOING }, //TODO
                            completed = resultt.filter { it.status == RequestStatus.COMPLETED }, //TODO
                            canceled = resultt.filter { it.status == RequestStatus.CANCELLED } //TODO
                        )
                    )
                )
                getOffersForRequest()
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

    private fun getOffersForRequest() {
        tryToExecute(
            execute = {
//                //getOffersUseCase(requestId)
//                screenState.value.myOffersCraftsmanUiState.requests.forEach { id, request ->
//                    getOffersUseCase(id).collect { offers ->
//                        updateState(
//                            screenState.value.copy(
//                                myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
//                                    requests = screenState.value.myOffersCraftsmanUiState.requests.toMutableMap().apply {
//                                        // TODO
//                                    }
//                                )
//                            )
//                        )
//                    }
//                }
            },
            onSuccess = { offers ->
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            offers = emptyList()//todo map to offer ui
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = it.message ?: "Failed to load offers"
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
                    listOf(screenState.value.myOffersCraftsmanUiState.customerPhone, phoneNumber)
                )
            },
            onSuccess = { chatId ->
                navigate(
                    Destinations.MessageDetails(
                        chatId = chatId,
                        currentUserId = screenState.value.myOffersCraftsmanUiState.customerPhone,
                        otherUserId = phoneNumber
                    )
                )
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