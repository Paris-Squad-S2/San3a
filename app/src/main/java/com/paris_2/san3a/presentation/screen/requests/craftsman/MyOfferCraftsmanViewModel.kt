package com.paris_2.san3a.presentation.screen.requests.craftsman

import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetCraftManAcceptedOfferOnRequestUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCraftsManRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MyOfferCraftsmanViewModel(
    private val getCraftsManRequestsUseCase: GetCraftsManRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val getCraftManAcceptedOfferOnRequestUseCase: GetCraftManAcceptedOfferOnRequestUseCase,
    private val createChatUseCase: CreateChatUseCase,
) : BaseViewModel<MyJobsCraftsmanScreenState>(MyJobsCraftsmanScreenState()),
    MyJobCraftsmanInteractionListener {

    init {
        getCraftsManPhone()
    }

    private fun getCraftsManPhone() {
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
                            craftsManId = phoneNumber
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
                getCraftsManRequestsUseCase(screenState.value.myOffersCraftsmanUiState.craftsManId)
            },
            onEach = { result ->
//                val resultt = result.toMyJobOfferUiStateList() //TODO
                updateState(
                    MyJobsCraftsmanScreenState(
                        isLoading = false,
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            requests = result.toMyJobOfferUiStateMap(),
//                            ongoing = resultt.filter { it.status == RequestStatus.ONGOING }, //TODO
//                            completed = resultt.filter { it.status == RequestStatus.COMPLETED }, //TODO
//                            canceled = resultt.filter { it.status == RequestStatus.CANCELLED } //TODO
                        )
                    )
                )
                getOffersForRequests()
            },
            onError = {
                updateState(
                    MyJobsCraftsmanScreenState(
                        isLoading = false,
                        errorMessage = it.message
                    )
                )
            }
        )
    }

    private fun getOffersForRequests() {
        tryToExecute(
            execute = {
//                //getOffersUseCase(requestId)
                screenState.value.myOffersCraftsmanUiState.requests.forEach { id, request ->
                    updateRequestOffer(requestId = id)
                }
            },
            onSuccess = {
                val result = screenState.value.myOffersCraftsmanUiState.requests.values.toList()
                updateState(
                    MyJobsCraftsmanScreenState(
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
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = it.message ?: "Failed to load offers"
                    )
                )
            }
        )
    }

    private fun updateRequestOffer(requestId: String) {
        tryToExecute(
            execute = {
                getCraftManAcceptedOfferOnRequestUseCase(
                    requestId = requestId,
                    craftsManId = screenState.value.myOffersCraftsmanUiState.craftsManId
                )
            },
            onSuccess = { offerFlow ->
                offerFlow.collect { offer ->
                    val updatedRequests =
                        screenState.value.myOffersCraftsmanUiState.requests.toMutableMap()

                    updatedRequests[requestId] = updatedRequests[requestId]?.copy(
                        offer = offer?.toUiState()
                    ) ?: return@collect

                    updateState(
                        screenState.value.copy(
                            myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                                requests = updatedRequests
                            ),
                        )
                    )
                }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = it.message ?: "Failed to load offers for request $requestId"
                    )
                )
            },
        )
    }


    override fun onSendAsDone(requestId: String) {
        /*TODO("Not yet implemented")*/
    }

    override fun onSendMessageClick(phoneNumber: String) {
        tryToExecute(
            execute = {
                createChatUseCase(
                    listOf(screenState.value.myOffersCraftsmanUiState.craftsManId, phoneNumber)
                )
            },
            onSuccess = { chatId ->
                navigate(
                    Destinations.MessageDetails(
                        chatId = chatId,
                        currentUserId = screenState.value.myOffersCraftsmanUiState.craftsManId,
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