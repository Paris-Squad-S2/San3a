package com.paris_2.san3a.presentation.screen.requestDetails.customer

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.usecase.requestDetails.AcceptOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.toOfferUiStateMap
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.toRequestServiceUIState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CustomerRequestDetailsRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val acceptOfferUseCase: AcceptOfferUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<CustomerRequestDetailsScreenState>(CustomerRequestDetailsScreenState()), CustomerRequestDetailsInteractionListener {



    val requestId = savedStateHandle.toRoute<Destinations.RequestDetails>().requestId
    val phoneNumber = savedStateHandle.toRoute<Destinations.RequestDetails>().phoneNumber

    init {
        loadRequestDetails(requestId)
        loadOffers(requestId)
    }

    fun loadRequestDetails(requestId: String){
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

    fun loadOffers(requestId: String){
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
        TODO("Not yet implemented")
    }

    override fun onChartWithCraftsmanClick(craftsmanId: String) {
        TODO("Not yet implemented")
    }

    override fun onAcceptOfferClick(offerId: String) {
        TODO("Not yet implemented")
    }


}