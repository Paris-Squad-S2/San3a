package com.paris_2.san3a.presentation.screen.requests.craftsman

import android.util.Log
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetCraftManOfferOnRequestUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.MarkRequestAsDoneUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCraftsManRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class MyJobsCraftsmanViewModel(
    private val getCraftsManRequestsUseCase: GetCraftsManRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val markRequestAsDoneUseCase: MarkRequestAsDoneUseCase,
    private val getRatingForCraftsmanUseCase: GetRatingForCraftsmanUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val getCraftManOfferOnRequestUseCase: GetCraftManOfferOnRequestUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val getAllServicesUseCase: GetAllServicesUseCase,
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
            onSuccess = ::onGetCraftsManPhoneSuccess,
            onError = ::onGetCraftsManPhoneError
        )
    }

    private fun onGetCraftsManPhoneSuccess(phoneNumber: String) {
        updateState(
            screenState.value.copy(
                isNoInternet = false,
                errorMessage = null,
                showSnackBarError = false,
                isLoading = false,
                myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                    craftsManId = phoneNumber
                )
            )
        )
        getUserServices()
        getCraftsManOfferOnRequest()
    }

    private fun onGetCraftsManPhoneError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                isNoInternet = false,
                errorMessage = R.string.phone_number_not_found,
                showSnackBarError = true,
                isLoading = false
            )
        )
    }

    private fun getCraftsManOfferOnRequest() {
        tryToObserve(
            observe = {
                getCraftsManRequestsUseCase(screenState.value.myOffersCraftsmanUiState.craftsManId)
            },
            onEach = ::onGetCraftsManOfferOnRequestEach,
            onError = ::onGetCraftsManOfferOnRequestError
        )
    }

    private fun onGetCraftsManOfferOnRequestEach(result: List<RequestService>?) {
        Log.d("MyOfferCraftsmanViewModel", "Fetched requests: $result")
        val filteredResult =
            result?.filter { it.selectedCraftsmanId.isNullOrBlank() || it.selectedCraftsmanId == screenState.value.myOffersCraftsmanUiState.craftsManId }
                ?: emptyList()
        Log.d("MyOfferCraftsmanViewModel", "Filtered requests: $filteredResult")
        val ongoing = filteredResult.filter { it.requestStatus == RequestStatus.ONGOING }
            .toMyJobOfferUiStateMap()
        val completed = filteredResult.filter { it.requestStatus == RequestStatus.COMPLETED }
            .toMyJobOfferUiStateMap()
        val canceled = filteredResult.filter { it.requestStatus == RequestStatus.CANCELLED }
            .toMyJobOfferUiStateMap()

        val updatedOngoing = ongoing.mapValues { (_, ui) ->
            ui.copy(serviceImage = serviceIdToImage[ui.serviceId] ?: ui.serviceImage)
        }
        val updatedCompleted = completed.mapValues { (_, ui) ->
            ui.copy(serviceImage = serviceIdToImage[ui.serviceId] ?: ui.serviceImage)
        }
        val updatedCanceled = canceled.mapValues { (_, ui) ->
            ui.copy(serviceImage = serviceIdToImage[ui.serviceId] ?: ui.serviceImage)
        }

        updateState(
            MyJobsCraftsmanScreenState(
                isLoading = false,
                errorMessage = null,
                showSnackBarError = false,
                myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                    ongoing = updatedOngoing,
                    completed = updatedCompleted,
                    canceled = updatedCanceled
                )
            )
        )
        getOffersForRequests()
    }

    private fun onGetCraftsManOfferOnRequestError(throwable: Throwable) {
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false
                )
            )
        } else {
            Log.d("MyJobsCraftsmanViewModel", "$throwable")
            updateState(
                MyJobsCraftsmanScreenState(
                    isLoading = false,
                    isNoInternet = false,
                    errorMessage = R.string.occurred_while_fetching_requests,
                    showSnackBarError = true
                )
            )
        }

    }

    private fun getOffersForRequests() {
        tryToExecute(
            execute = {
                screenState.value.myOffersCraftsmanUiState.ongoing.map { mapEntry ->
                    updateRequestOffer(requestId = mapEntry.key, listType = ListType.ONGOING)
                }
                screenState.value.myOffersCraftsmanUiState.completed.map { mapEntry ->
                    updateRequestOffer(requestId = mapEntry.key, listType = ListType.COMPLETED)
                }
                screenState.value.myOffersCraftsmanUiState.canceled.map { mapEntry ->
                    updateRequestOffer(requestId = mapEntry.key, listType = ListType.CANCELED)
                }
            },
            onError = ::onGetOffersForRequestsError
        )
    }

    private fun onGetOffersForRequestsError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = R.string.error_fetching_offers,
                showSnackBarError = true
            )
        )
    }

    private fun updateRequestOffer(requestId: String, listType: ListType) = tryToObserve(
        observe = {
            getCraftManOfferOnRequestUseCase(
                requestId = requestId,
                craftsManId = screenState.value.myOffersCraftsmanUiState.craftsManId
            )
        },
        onEach = { onUpdateRequestOfferEach(it, requestId, listType) },
        onError = ::onUpdateRequestOfferError,
    )

    private fun onUpdateRequestOfferError(throwable: Throwable) {
        Log.e(
            "MyOfferCraftsmanViewModel",
            "Error fetching offer for request"
        )
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    isNoInternet = false,
                    showSnackBarError = true,
                    errorMessage = R.string.failed_to_load_offers_for_request
                )
            )
        }

    }

    private fun onUpdateRequestOfferEach(offer: Offer?, requestId: String, listType: ListType) {
        if (offer == null) {
            Log.d("MyOfferCraftsmanViewModel", "No offer found for request $requestId")
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    errorMessage = R.string.no_offer_found_for_request,
                    showSnackBarError = true
                )
            )
        }

        val updatedRequests = when (listType) {
            ListType.ONGOING -> screenState.value.myOffersCraftsmanUiState.ongoing.toMutableMap()
            ListType.COMPLETED -> screenState.value.myOffersCraftsmanUiState.completed.toMutableMap()
            ListType.CANCELED -> screenState.value.myOffersCraftsmanUiState.canceled.toMutableMap()
        }

        Log.d("MyOfferCraftsmanViewModel", "Updating request $requestId with offer: $offer")

        updatedRequests[requestId] = updatedRequests[requestId]?.copy(
            offer = offer.toUiState()
        ) ?: return

        when (listType) {
            ListType.ONGOING -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        ongoing = updatedRequests
                    )
                )
            )

            ListType.COMPLETED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        completed = updatedRequests
                    )
                )
            )

            ListType.CANCELED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        canceled = updatedRequests
                    )
                )
            )
        }
        offer?.let {
            getCraftsManDetails(
                requestId = requestId,
                craftsManId = it.craftsmanId,
                listType = listType
            )
        }

    }

    private fun getCraftsManDetails(
        requestId: String,
        craftsManId: String,
        listType: ListType
    ) {
        tryToExecute(
            execute = { scope ->
                val craftsManDeferred = scope.async { getUserUseCase(craftsManId) }
                val ratingDeferred =
                    scope.async { getRatingForCraftsmanUseCase(craftsManId).first() }

                craftsManDeferred.await() to ratingDeferred.await()
            },
            onSuccess = { (craftsMan, rating) ->
                onGetCraftsManDetailsSuccess(craftsMan, rating, listType, requestId)
            },
            onError = ::onGetCraftsManDetailsError
        )
    }

    private fun onGetCraftsManDetailsError(throwable: Throwable) {
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    isNoInternet = false,
                    showSnackBarError = true,
                    errorMessage = R.string.error_fetching_craftsman_details
                )
            )
        }

    }

    private fun onGetCraftsManDetailsSuccess(
        craftsMan: User,
        rating: Float,
        listType: ListType,
        requestId: String
    ) {
        Log.d("MyOfferCraftsmanViewModel", "Fetched craftsman details: $craftsMan")
        val updatedRequests = when (listType) {
            ListType.ONGOING -> screenState.value.myOffersCraftsmanUiState.ongoing.toMutableMap()
            ListType.COMPLETED -> screenState.value.myOffersCraftsmanUiState.completed.toMutableMap()
            ListType.CANCELED -> screenState.value.myOffersCraftsmanUiState.canceled.toMutableMap()
        }

        updatedRequests[requestId] = updatedRequests[requestId]?.copy(
            offer = updatedRequests[requestId]?.offer?.copy(
                craftsMan = craftsMan.toCraftsManUiState(rating)
            )
        ) ?: return

        when (listType) {
            ListType.ONGOING -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        ongoing = updatedRequests
                    )
                )
            )

            ListType.COMPLETED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        completed = updatedRequests
                    )
                )
            )

            ListType.CANCELED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        canceled = updatedRequests
                    )
                )
            )
        }
    }

    private var serviceIdToImage: Map<String, String> = emptyMap()

    private fun getUserServices() {
        tryToObserve(
            observe = getAllServicesUseCase::invoke,
            onEach = { servicesList ->
                serviceIdToImage = (servicesList ?: emptyList()).associate { it.id to it.imageUrl }

                // Update only the serviceImage fields for current items
                val current = screenState.value.myOffersCraftsmanUiState
                val updatedOngoing = current.ongoing.mapValues { (_, ui) ->
                    ui.copy(serviceImage = serviceIdToImage[ui.serviceId] ?: ui.serviceImage)
                }
                val updatedCompleted = current.completed.mapValues { (_, ui) ->
                    ui.copy(serviceImage = serviceIdToImage[ui.serviceId] ?: ui.serviceImage)
                }
                val updatedCanceled = current.canceled.mapValues { (_, ui) ->
                    ui.copy(serviceImage = serviceIdToImage[ui.serviceId] ?: ui.serviceImage)
                }

                updateState(
                    screenState.value.copy(
                        myOffersCraftsmanUiState = current.copy(
                            ongoing = updatedOngoing,
                            completed = updatedCompleted,
                            canceled = updatedCanceled,
                        )
                    )
                )
            }
        )
    }

    override fun onMarkAsDone(requestId: String, requestTitle: String, customerId: String) {
        tryToExecute(
            execute = {
                markRequestAsDoneUseCase(requestId)
            },
            onSuccess = {
                onMarkAsDoneSuccess(requestTitle, customerId)
            },
            onError = ::onMarkAsDoneError

        )
    }

    private fun onMarkAsDoneError(throwable: Throwable) {
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    isNoInternet = false,
                    showSnackBarError = true,
                    errorMessage = R.string.error_marking_request_as_done
                )
            )
        }
    }

    private fun onMarkAsDoneSuccess(requestTitle: String, customerId: String) {
        tryToExecute(
            execute = {
                addNotificationUseCase(
                    Notification(
                        id = "",
                        title = "Request Completed",
                        caption = "Your request $requestTitle has been marked as done.",
                        date = getCurrentDateTime(),
                        userId = customerId
                    )
                )
            },
            onError = ::onAddNotificationError
        )

    }

    private fun onAddNotificationError(throwable: Throwable) {
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    isNoInternet = false,
                    showSnackBarError = true,
                    errorMessage = R.string.some_error_happened
                )
            )
        }
    }

    override fun onSendMessageClick(phoneNumber: String) {
        tryToExecute(
            execute = {
                createChatUseCase(
                    listOf(screenState.value.myOffersCraftsmanUiState.craftsManId, phoneNumber)
                )
            },
            onSuccess = { chatId ->
                onSendMessageClickSuccess(chatId, phoneNumber)
            },
            onError = ::onSendMessageClickError
        )
    }

    private fun onSendMessageClickSuccess(chatId: String, phoneNumber: String) {
        navigate(
            Destinations.MessageDetails(
                chatId = chatId,
                currentUserId = screenState.value.myOffersCraftsmanUiState.craftsManId,
                otherUserId = phoneNumber
            )
        )
    }

    private fun onSendMessageClickError(throwable: Throwable) {
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    isNoInternet = false,
                    showSnackBarError = true,
                    errorMessage = R.string.occurred_while_sending_message_to_customer
                )
            )
        }
    }

    override fun onViewRequestDetails(requestId: String) {
        navigate(
            Destinations.RequestDetails(
                requestId = requestId,
                phoneNumber = screenState.value.myOffersCraftsmanUiState.craftsManId,
            )
        )
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onRetryClick() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                isNoInternet = false,
                errorMessage = null,
                showSnackBarError = false
            )
        )
        getUserServices()
        getCraftsManOfferOnRequest()
    }

    override fun onDismissSnackBar() {
        updateState(
            screenState.value.copy(
                showSnackBarError = false,
                errorMessage = null,
            )
        )
    }
}