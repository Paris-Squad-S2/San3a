package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.AddOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CraftsmanRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val addOfferUseCase: AddOfferUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createChatUseCase: CreateChatUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CraftsmanRequestDetailsScreenState>(CraftsmanRequestDetailsScreenState()),
    CraftsmanRequestDetailsInteractionListener {

    val requestId = savedStateHandle.toRoute<Destinations.RequestDetails>().requestId
    val phoneNumber = savedStateHandle.toRoute<Destinations.RequestDetails>().phoneNumber

    init {
        loadRequestDetails(requestId)
        loadOffers(requestId)
    }

    fun loadRequestDetails(requestId: String) {
        tryToExecute(
            execute = { getRequestDetailsByIdUseCase(requestId) },
            onSuccess = {
                Log.d("CraftsmanRequestDetailsVM", "Request details loaded: $it")
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            request = it.toRequestServiceUIState(),
                        ),
                    )
                )
                getCustomer(it.userId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred",
                    )
                )
            }
        )
    }

    fun loadOffers(requestId: String) {
        tryToObserve(
            observe = { getOffersUseCase(requestId) },
            onEach = {
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            offers = it.toOfferUiStateList()
//                            offers = offerList //TODO
                        ),
                    )
                )
                loadOffersFromCraftsman()
                loadYourOffers()
                loadAcceptedOffers()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while loading offers",
                    )
                )
            }
        )
    }

    private fun loadOffersFromCraftsman() {
        tryToExecute(
            execute = {
                screenState.value.uiState.offers.filter { it.craftsmanId != phoneNumber && it.isAccepted.not() }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            offersFromCraftsman = it
                        ),
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while loading craftsman offers",
                    )
                )
            }
        )
    }

    override fun onClickAddOffer(offer: OfferUiState) {
        tryToExecute(
            execute = { addOfferUseCase(offer.toEntity()) },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while adding offer",
                    )
                )
            }
        )
    }

    override fun onClickSendMessage(customerId: String) {
        //TODO("Not yet implemented")
    }

    override fun onSendOfferClick() {
        // TODO("Not yet implemented")
    }

    override fun onChatWithPosterClick(customerId: String) {
        tryToExecute(
            execute = { createChatUseCase(listOf(phoneNumber, customerId)) },
            onSuccess = {
                Log.d("CraftsmanRequestDetailsVM", "Chat created successfully")
                navigate(
                    Destinations.MessageDetails(
                        chatId = it,
                        currentUserId = phoneNumber,
                        otherUserId = customerId
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while creating chat",
                    )
                )
            }
        )
    }

    override fun onAcceptOfferClick(offerId: String) {
        //TODO("Not yet implemented")
    }

    override fun onClickFavorite() {
//        TODO("Not yet implemented")
    }

    override fun onClickBack() {
        navigateUp()
    }

    override fun onRetryClick() {
//        TODO("Not yet implemented")
    }

    fun loadYourOffers() {
        tryToExecute(
            execute = {
                screenState.value.uiState.offers.filter { it.craftsmanId == phoneNumber }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            yourOffers = it
                        ),
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while loading your offers",
                    )
                )
            }
        )
    }

    fun loadAcceptedOffers() {
        tryToExecute(
            execute = {
                screenState.value.uiState.offers.firstOrNull { it.isAccepted }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            acceptedOffer = it
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while loading accepted offers",
                    )
                )
            }
        )
    }

    fun getCustomer(userId: String) {
        tryToExecute(
            execute = { getUserUseCase(userId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            customer = Customer(
                                id = it.id,
                                name = it.fullName,
                                profilePhoto = it.profilePhoto
                            )
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while loading customer details",
                    )
                )
            }
        )
    }
}