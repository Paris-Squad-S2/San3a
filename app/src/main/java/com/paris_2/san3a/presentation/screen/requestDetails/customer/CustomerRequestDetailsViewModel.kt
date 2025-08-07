package com.paris_2.san3a.presentation.screen.requestDetails.customer

import com.paris_2.san3a.domain.usecase.requestDetails.AcceptOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CustomerRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val acceptOfferUseCase: AcceptOfferUseCase
): BaseViewModel<CustomerRequestDetailsScreenState>(CustomerRequestDetailsScreenState()), CustomerInteractionListener {

    init {

    }

    fun loadRequestDetails(requestId: String){
        tryToExecute(
            execute = { getRequestDetailsByIdUseCase(requestId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customerRequestDetails = CustomerRequestDetails(
                            requestId = it.id,
                            title = it.title,
                            description = it.description,
                            serviceType = it.serviceType,
                            time = it.time.toString(),
                            location = it.location,
                            photos = it.image,
                        )
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
                        offers = it,
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

    override fun onClickDetails() {}


}