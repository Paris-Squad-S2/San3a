package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.usecase.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.AddOfferUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.CancelRequestUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.MarkRequestAsDoneUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class CraftsmanRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val addOfferUseCase: AddOfferUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val cancelRequestUseCase: CancelRequestUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val markRequestAsDoneUseCase: MarkRequestAsDoneUseCase,
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
                it.forEach { offer ->
                    Log.d("CraftsmanRequestDetailsVM", "Offer: ${offer.toOfferUiState()}")
                }
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            offers = it.toOfferUiStateMap()
                        ),
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
                loadOffersFromCraftsman()
                loadYourOffers()
                loadAcceptedOffers()
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

    private fun loadOffersFromCraftsman() {
        tryToExecute(
            execute = {
                screenState.value.uiState.offers.values.filter { it.craftsmanId != phoneNumber && it.isAccepted.not() }
            },
            onSuccess = {
                it.forEach { offer ->
                    Log.d("CraftsmanRequestDetailsVM", "Offer from craftsMen: $offer")
                }
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

    override fun onSendOfferClick() {
        tryToExecute(
            execute = {
                addOfferUseCase(
                    screenState.value.uiState.offerToAdd.toOffer(
                        craftsManId = phoneNumber,
                        requestId = requestId
                    )
                )
            },
            onSuccess = {
                Log.d("CraftsmanRequestDetailsVM", "Offer added successfully")
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            offerToAdd = OfferToAddUiState()
                        )
                    )
                )
                addNotificationUseCase(
                    Notification(
                        id = "",
                        title = "New Offer Received",
                        caption = "You have received a new offer for your request ${screenState.value.uiState.request.title}",
                        date = getCurrentDateTime(),
                        userId = screenState.value.uiState.request.userId
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while sending offer",
                    )
                )
            }
        )
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

    override fun onCancelRequestClick(requestId: String) {
        tryToExecute(
            execute = {
                cancelRequestUseCase(requestId)
            },
            onSuccess = {
                Log.d("CraftsmanRequestDetailsVM", "Request cancelled successfully")
                navigateUp()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while cancelling request",
                    )
                )
            }
        )
    }

    override fun markAsDoneClick(requestId: String) {
        tryToExecute(
            execute = {
                markRequestAsDoneUseCase(requestId)
            },
            onSuccess = {
                Log.d("CraftsmanRequestDetailsVM", "Request marked as done successfully")
                addNotificationUseCase(
                    Notification(
                        id = "",
                        title = "Request Completed",
                        caption = "Your request ${screenState.value.uiState.request.title} has been marked as done.",
                        date = getCurrentDateTime(),
                        userId = screenState.value.uiState.request.userId
                    )
                )
                navigateUp()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        error = it.message ?: "An error occurred while marking request as done",
                    )
                )
            }
        )
    }

    override fun onPriceChanged(price: String) {
        val updatedOffer = screenState.value.uiState.offerToAdd.copy(price = price)
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    offerToAdd = updatedOffer,
                    isOfferValid = validateOffer(updatedOffer)
                )
            )
        )
    }

    override fun onDateChanged(date: LocalDate) {
        val updatedOffer = screenState.value.uiState.offerToAdd.copy(preferredDate = date)
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    offerToAdd = updatedOffer,
                    isOfferValid = validateOffer(updatedOffer)
                )
            )
        )
    }

    override fun onTimeChanged(time: LocalTime) {
        val updatedOffer = screenState.value.uiState.offerToAdd.copy(preferredTime = time)
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    offerToAdd = updatedOffer,
                    isOfferValid = validateOffer(updatedOffer)
                )
            )
        )
    }

    override fun onMessageChanged(message: String) {
        val updatedOffer = screenState.value.uiState.offerToAdd.copy(messageToCustomer = message)
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    offerToAdd = updatedOffer,
                    isOfferValid = validateOffer(updatedOffer)
                )
            )
        )
    }


    override fun onShowDatePickerChange(show: Boolean) {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    showDatePicker = show
                )
            )
        )
    }

    override fun onShowTimePickerChange(show: Boolean) {
        updateState(
            screenState.value.copy(
                uiState = screenState.value.uiState.copy(
                    showTimePicker = show
                )
            )
        )
    }

    override fun onClickFavorite() {}

    override fun onClickBack() {
        navigateUp()
    }

    override fun onRetryClick() {
        loadRequestDetails(requestId)
        loadOffers(requestId)
    }

    fun loadYourOffers() {
        tryToExecute(
            execute = {
                screenState.value.uiState.offers.values.firstOrNull { it.craftsmanId == phoneNumber }
            },
            onSuccess = { offer ->
                Log.d("CraftsmanRequestDetailsVM", "your Offer: $offer")

                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            yourOffer = offer
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
                screenState.value.uiState.offers.values.firstOrNull { it.isAccepted }
            },
            onSuccess = {
                Log.d("CraftsmanRequestDetailsVM", "Accepted offer: $it")
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

    private fun validateOffer(offer: OfferToAddUiState): Boolean {
        return offer.price.isNotBlank()
                && offer.preferredDate != null
                && offer.preferredTime != null
                && offer.messageToCustomer.isNotBlank()
    }
}