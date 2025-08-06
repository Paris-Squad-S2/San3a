package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.AddOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetAcceptedOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetYourOfferUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CraftsmanRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val addOfferUseCase: AddOfferUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val getYourOfferUseCase: GetYourOfferUseCase,
    private val getAcceptOfferUseCase: GetAcceptedOffersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase
): BaseViewModel<CraftsmanRequestUiState>(CraftsmanRequestUiState()), CraftsmanInteractionListener
{
    init {

    }

    fun loadRequestDetails(requestId: String){
        tryToExecute(
            execute = { getRequestDetailsByIdUseCase(requestId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        craftsmanRequestDetails = CraftsmanRequestDetails(
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

    override fun onClickSendMessage(customerId: String) {}

    override fun onClickFavorite() {
        TODO("Not yet implemented")
    }

    fun loadYourOffers(craftsmanId: String){
        tryToExecute(
            execute = { getYourOfferUseCase(craftsmanId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        yourOffer = it
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

    fun loadAcceptedOffers(requestId: String) {
        tryToObserve(
            observe = { getAcceptOfferUseCase(requestId) },
            onEach = {
                updateState(
                    screenState.value.copy(
                        acceptedOffers = it
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

    fun getCustomer(){
        tryToExecute(
            execute = { getUserUseCase(getPhoneNumberUseCase())},
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customer = Customer(
                            id = it.id,
                            name = it.fullName,
                            profilePhoto = it.profilePhoto
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