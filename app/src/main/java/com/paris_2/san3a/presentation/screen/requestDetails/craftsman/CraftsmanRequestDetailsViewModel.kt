package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.entity.NotificationToSend
import com.paris_2.san3a.domain.usecase.notification.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.location.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.user.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.services.GetServiceByIdUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.IncrementJobsDoneForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.UpdateEarningsForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.messaging.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requests.AddOfferUseCase
import com.paris_2.san3a.domain.usecase.requests.CancelRequestUseCase
import com.paris_2.san3a.domain.usecase.requests.GetOffersUseCase
import com.paris_2.san3a.domain.usecase.requests.GetRequestDetailsByIdUseCase
import com.paris_2.san3a.domain.usecase.requests.MarkRequestAsDoneUseCase
import com.paris_2.san3a.presentation.utill.fakeImage
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class CraftsmanRequestDetailsViewModel(
    private val getRequestDetailsByIdUseCase: GetRequestDetailsByIdUseCase,
    private val addOfferUseCase: AddOfferUseCase,
    private val getOffersUseCase: GetOffersUseCase,
    private val cancelRequestUseCase: CancelRequestUseCase,
    private val getRatingForCraftsmanUseCase: GetRatingForCraftsmanUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val markRequestAsDoneUseCase: MarkRequestAsDoneUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val getServiceByIdUseCase: GetServiceByIdUseCase,
    private val incrementJobsDoneForCraftsmanUseCase: IncrementJobsDoneForCraftsmanUseCase,
    private val updateEarningsForCraftsmanUseCase: UpdateEarningsForCraftsmanUseCase,
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
                val governorate = getLocationInfoUseCase.getGovernorateById(it.governorateId)
                val city = getLocationInfoUseCase.getCityById(it.cityId)
                Log.d("CraftsmanRequestDetailsVM", "Request details loaded: $it")
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            request = it.toRequestServiceUIState(
                                location =
                                    listOfNotNull(
                                        governorate?.name,
                                        city?.name
                                    ).joinToString(", ")
                            ),
                        ),
                        isLoading = false,
                        error = null,
                    )
                )
                getCustomer(it.userId)
                getServiceDetails(it.serviceId)
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
            onEach = {
                it?.forEach { offer ->
                    Log.d("CraftsmanRequestDetailsVM", "Offer: ${offer.toOfferUiState()}")
                }
                updateState(
                    screenState.value.copy(
                        uiState = screenState.value.uiState.copy(
                            offers = it?.toOfferUiStateMap() ?: emptyMap()
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
                    screenState.value.uiState.request.userId,
                    NotificationToSend(
                        title = mapOf(
                            "en" to "New Offer Received",
                            "ar" to "تم استلام عرض جديد"
                        ),
                        caption = mapOf(
                            "en" to "You have received a new offer for your request ${screenState.value.uiState.request.title}",
                            "ar" to "لقد استلمت عرضًا جديدًا لطلبك '${screenState.value.uiState.request.title}'"
                        ),
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

    override fun markAsDoneClick(requestId: String, price: Double) {
        tryToExecute(
            execute = { scope ->
                markRequestAsDoneUseCase(requestId)
                val incrementJob = scope.async {
                    incrementJobsDoneForCraftsmanUseCase(
                        craftsmanId = phoneNumber,
                        requestId = requestId,
                        userId = screenState.value.uiState.request.userId
                    )
                }
                val updateEarningsJob =
                    scope.async {
                        updateEarningsForCraftsmanUseCase(
                            craftsmanId = phoneNumber,
                            userId = screenState.value.uiState.request.userId,
                            requestId = requestId,
                            earnings = price
                        )
                    }
                incrementJob.await()
                updateEarningsJob.await()
            },
            onSuccess = {
                Log.d("CraftsmanRequestDetailsVM", "Request marked as done successfully")
                addNotificationUseCase(
                    screenState.value.uiState.request.userId,
                    NotificationToSend(
                        title = mapOf(
                            "en" to "Request Completed",
                            "ar" to "تم الانتهاء من الطلب"
                        ),
                        caption = mapOf(
                            "en" to "Your request ${screenState.value.uiState.request.title} has been marked as done.",
                            "ar" to "تم الانتهاء من طلبك '${screenState.value.uiState.request.title}'"
                        ),
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
        if (isInputPriceValid(price)) {
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
    }
    private fun isInputPriceValid(price: String): Boolean {
        return price.all{ it.isDigit()} && price.length<=7
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
                                profilePhoto = it.profilePhoto ?: fakeImage
                            )
                        )
                    )
                )
            },
            onError = {
                Log.d("CraftsmanRequestDetailsVM", "Error loading customer: ${it.message}")
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