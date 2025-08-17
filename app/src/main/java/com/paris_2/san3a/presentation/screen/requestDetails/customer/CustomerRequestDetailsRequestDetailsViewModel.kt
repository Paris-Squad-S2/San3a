package com.paris_2.san3a.presentation.screen.requestDetails.customer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.usecase.notification.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.GetServiceByIdUseCase
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
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class CustomerRequestDetailsRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val acceptOfferUseCase: AcceptOfferUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val getServiceByIdUseCase: GetServiceByIdUseCase,
    private val getRatingForCraftsmanUseCase: GetRatingForCraftsmanUseCase,
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
            onSuccess = { request ->
                val governorate = getLocationInfoUseCase.getGovernorateById(request.governorateId)
                val city = getLocationInfoUseCase.getCityById(request.cityId)
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        error = null,
                        uiState = screenState.value.uiState.copy(
                            request = request.toRequestServiceUIState(
                                location =
                                    listOfNotNull(
                                        governorate?.name,
                                        city?.name
                                    ).joinToString(", ")
                            ),
                        ),
                    )
                )
                getServiceDetails(request.serviceId)
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

    private fun getServiceDetails(serviceId: String) {
        tryToExecute(
            execute = { getServiceByIdUseCase(serviceId) },
            onSuccess = { service ->
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            request = screenState.value.uiState.request.copy(
                                serviceType = service?.title ?: "Unknown Service",
                                serviceImageUri = service?.imageUrl.orEmpty(),
                            )
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while loading service details",
                    )
                )
            }
        )
    }

    fun loadOffers(requestId: String) {
        tryToObserve(
            observe = { getOffersUseCase(requestId) },
            onEach = { offers ->
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        error = null,
                        uiState = screenState.value.uiState.copy(
                            offers = offers?.sortedByDescending { it.isAccepted }
                                ?.toOfferUiStateMap()
                                ?: emptyMap(),
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
            execute = { scope ->
                screenState.value.uiState.offers.forEach { offer ->
                    val craftsmanId = offer.value.craftsmanId
                    val userDeferred = scope.async { getUserUseCase(craftsmanId) }
                    val ratingDeferred =
                        scope.async { getRatingForCraftsmanUseCase(craftsmanId).first() }
                    val user = userDeferred.await()
                    val rating = ratingDeferred.await()
                    user.toRequestOfferUiState(offer.value, rating).also { offerUiState ->
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

    override fun onAcceptOfferClick(offerId: String, craftsmanId: String) {
        tryToExecute(
            execute = {
                acceptOfferUseCase(
                    offerId = offerId,
                    craftsmanId = craftsmanId,
                    requestId = requestId
                )
            },
            onSuccess = {
                addNotificationUseCase(
                    Notification(
                        id = "",
                        title = "New Offer Accepted",
                        caption = "Your offer for request '${screenState.value.uiState.request.title}' has been accepted.",
                        date = getCurrentDateTime(),
                        userId = craftsmanId
                    )
                )
            },
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