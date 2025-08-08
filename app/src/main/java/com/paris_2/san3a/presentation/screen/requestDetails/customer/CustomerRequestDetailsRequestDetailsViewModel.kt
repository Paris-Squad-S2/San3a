package com.paris_2.san3a.presentation.screen.requestDetails.customer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.AcceptOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.toOfferUiStateMap
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.toRequestOfferUiState
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.toRequestServiceUIState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CustomerRequestDetailsRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val acceptOfferUseCase: AcceptOfferUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createChatUseCase: CreateChatUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CustomerRequestDetailsScreenState>(CustomerRequestDetailsScreenState()),
    CustomerRequestDetailsInteractionListener {


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
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        error = null,
                        uiState = screenState.value.uiState.copy(
                            request = it.toRequestServiceUIState(),
                        ),
                    )
                )
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
                        isLoading = false,
                        error = null,
                        uiState = screenState.value.uiState.copy(
                            offers = it.toOfferUiStateMap()
                        )
                    )
                )
                loadCraftsMenInfo()
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

    private fun loadCraftsMenInfo() {
        tryToExecute(
            execute = {
                screenState.value.uiState.offers.forEach { offer ->
                    getUserUseCase(offer.value.craftsmanId).also { user ->
                        user.toRequestOfferUiState(offer.value).also { offerUiState ->
                            Log.d("CraftsmanRequestDetailsVM", "Craftsman info: $offerUiState")
                            updateState(
                                screenState.value.copy(
                                    uiState = screenState.value.uiState.copy(
                                        offers = screenState.value.uiState.offers.toMutableMap()
                                            .apply {
                                                this[offer.key] = offerUiState
                                            }
                                    )
                                )
                            )
                        }
                    }
                }
            },
            onSuccess = {

            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while loading craftsmen info",
                    )
                )

            }
        )

    }

    override fun onClickOffer(offerId: String) {
        tryToExecute(
            execute = { acceptOfferUseCase(offerId) },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while accepting the offer",
                    )
                )
            }
        )
    }

    override fun onClickBack() {
        navigateUp()
    }

    override fun onClickActonDots() {
//        TODO("Not yet implemented")
    }

    override fun onRetryClick() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                error = null
            )
        )
        loadRequestDetails(requestId)
        loadOffers(requestId)
    }

    override fun onChartWithCraftsmanClick(craftsmanId: String) {
        tryToExecute(
            execute = { createChatUseCase(listOf(craftsmanId, phoneNumber)) },
            onSuccess = { chatId ->
                navigate(Destinations.MessageDetails(chatId, phoneNumber, craftsmanId))
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
        tryToExecute(
            execute = { acceptOfferUseCase(offerId) },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while accepting the offer",
                    )
                )
            }
        )
    }


}