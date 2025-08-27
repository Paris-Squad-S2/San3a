package com.paris_2.san3a.presentation.screen.requests.craftsman

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.exceptions.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.NotificationToSend
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.services.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.location.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messaging.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.notification.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCraftManOfferOnRequestUseCase
import com.paris_2.san3a.domain.usecase.requests.MarkRequestAsDoneUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCraftsManRequestsUseCase
import com.paris_2.san3a.domain.usecase.user.IncrementJobsDoneForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.UpdateEarningsForCraftsmanUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.shared.utils.UiText
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyJobsCraftsmanViewModel(
    private val getCraftsManRequestsUseCase: GetCraftsManRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val markRequestAsDoneUseCase: MarkRequestAsDoneUseCase,
    private val incrementJobsDoneForCraftsmanUseCase: IncrementJobsDoneForCraftsmanUseCase,
    private val updateEarningsForCraftsmanUseCase: UpdateEarningsForCraftsmanUseCase,
    private val getRatingForCraftsmanUseCase: GetRatingForCraftsmanUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val getUnReadNotificationsCountUseCase: GetUnReadNotificationsCountUseCase,
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
                showSnackBarError = false,
                myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                    craftsManId = phoneNumber,
                )
            )
        )
        getUserServices()
        getNotificationsCount(phoneNumber)
        getCraftsManOfferOnRequest()
    }

    private fun getNotificationsCount(userId: String) {
        tryToObserve(
            observe = {
                getUnReadNotificationsCountUseCase(userId)
            },
            onEach = { count ->
                updateState(
                    screenState.value.copy(
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            notificationsCount = count ?: 0,
                        )
                    )
                )
            },
            onError = { exception ->
                Log.e(
                    "MessagesViewModel",
                    "Error fetching notifications count: ${exception.message}"
                )
            },
        )
    }


    private fun onGetCraftsManPhoneError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                isNoInternet = false,
                errorMessage = UiText.StringResource(R.string.phone_number_not_found),
                showSnackBarError = true,
                isLoading = false
            )
        )
        hideSnackBar()
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
        tryToExecute(
            execute = {
                Log.d("MyOfferCraftsmanViewModel", "Fetched requests: $result")
                val filteredResult =
                    result?.filter { it.selectedCraftsmanId.isNullOrBlank() || it.selectedCraftsmanId == screenState.value.myOffersCraftsmanUiState.craftsManId }
                        ?: emptyList()
                Log.d("MyOfferCraftsmanViewModel", "Filtered requests: $filteredResult")
                filteredResult.map { request ->
                    val governorate =
                        getLocationInfoUseCase.getGovernorateById(request.governorateId)
                    val city = getLocationInfoUseCase.getCityById(request.cityId)
                    request.toMyJobOfferUiState(
                        location = "${governorate?.name.orEmpty()} ${city?.name.orEmpty()}",
                        serviceImage = screenState.value.myOffersCraftsmanUiState.userServices[request.serviceId]?.imageUrl.orEmpty()
                    )
                }
            },
            onSuccess = { mappedResult ->
                updateState(
                    MyJobsCraftsmanScreenState(
                        showSnackBarError = false,
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            ongoing = mappedResult.filter { it.status == RequestStatus.ONGOING }
                                .associateBy { it.id },
                            completed = mappedResult.filter { it.status == RequestStatus.COMPLETED }
                                .associateBy { it.id },
                            canceled = mappedResult.filter { it.status == RequestStatus.CANCELLED }
                                .associateBy { it.id },
                        )
                    )
                )
                getOffersForRequests()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                    )
                )
                Log.e("MyJobsCraftsmanViewModel", "Error mapping requests: ${it.message}")
            }
        )
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
                    errorMessage = UiText.StringResource(R.string.occurred_while_fetching_requests),
                    showSnackBarError = true
                )
            )
            hideSnackBar()
        }

    }

    private fun getOffersForRequests() {
        tryToExecute(
            execute = {
                screenState.value.myOffersCraftsmanUiState.ongoing.forEach { mapEntry ->
                    updateRequestOffer(requestId = mapEntry.key, listType = ListType.ONGOING)
                }
                screenState.value.myOffersCraftsmanUiState.completed.forEach { mapEntry ->
                    updateRequestOffer(requestId = mapEntry.key, listType = ListType.COMPLETED)
                }
                screenState.value.myOffersCraftsmanUiState.canceled.forEach { mapEntry ->
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
                errorMessage = UiText.StringResource(R.string.error_fetching_offers),
                showSnackBarError = true
            )
        )
        hideSnackBar()
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
                    errorMessage = UiText.StringResource(R.string.failed_to_load_offers_for_request)
                )
            )
            hideSnackBar()
        }

    }

    private suspend fun onUpdateRequestOfferEach(offer: Offer?, requestId: String, listType: ListType) {
        if (offer == null) {
            delay(TIMEOUT)
            updateState(
                screenState.value.copy(
                    isLoading = false,
                )
            )
            Log.d("MyOfferCraftsmanViewModel", "No offer found for request $requestId")
            return
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
                        ongoing = updatedRequests,
                    )
                )
            )

            ListType.COMPLETED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        completed = updatedRequests,
                    )
                )
            )

            ListType.CANCELED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        canceled = updatedRequests,
                    )
                )
            )
        }
        getCraftsManDetails(
            requestId = requestId,
            craftsManId = offer.craftsmanId,
            listType = listType
        )

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
                    errorMessage = UiText.StringResource(R.string.error_fetching_craftsman_details)
                )
            )
            hideSnackBar()
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
                        ongoing = updatedRequests,
                    ),
                    isLoading = false,
                )
            )

            ListType.COMPLETED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        completed = updatedRequests,
                    ),
                    isLoading = false,
                )
            )

            ListType.CANCELED -> updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                        canceled = updatedRequests,
                    ),
                    isLoading = false,
                )
            )
        }
    }

    private fun getUserServices() {
        tryToObserve(
            observe = getAllServicesUseCase::invoke,
            onEach = { servicesList ->
                updateState(
                    screenState.value.copy(
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            userServices = servicesList?.associateBy { it.id } ?: emptyMap()
                        ),
                    )
                )

                // Update only the serviceImage fields for current items
                val current = screenState.value.myOffersCraftsmanUiState
                val updatedOngoing = current.ongoing.mapValues { (_, ui) ->
                    ui.copy(
                        serviceImage = screenState.value.myOffersCraftsmanUiState.userServices[ui.serviceId]?.imageUrl.orEmpty(),
                        serviceType = screenState.value.myOffersCraftsmanUiState.userServices[ui.serviceId]?.title ?: ui.serviceType
                    )
                }
                val updatedCompleted = current.completed.mapValues { (_, ui) ->
                    ui.copy(
                        serviceImage = screenState.value.myOffersCraftsmanUiState.userServices[ui.serviceId]?.imageUrl.orEmpty(),
                        serviceType = screenState.value.myOffersCraftsmanUiState.userServices[ui.serviceId]?.title ?: ui.serviceType
                    )
                }
                val updatedCanceled = current.canceled.mapValues { (_, ui) ->
                    ui.copy(
                        serviceImage = screenState.value.myOffersCraftsmanUiState.userServices[ui.serviceId]?.imageUrl.orEmpty(),
                        serviceType = screenState.value.myOffersCraftsmanUiState.userServices[ui.serviceId]?.title ?: ui.serviceType
                    )
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

    override fun onMarkAsDone(job: JobUiState) {
        tryToExecute(
            execute = { scope ->
                markRequestAsDoneUseCase(job.id)
                val incrementJob = scope.async {
                    incrementJobsDoneForCraftsmanUseCase(
                        craftsmanId = screenState.value.myOffersCraftsmanUiState.craftsManId,
                        requestId = job.id,
                        userId = job.customerPhone
                    )
                }
                val updateEarningsJob =
                    scope.async {
                        updateEarningsForCraftsmanUseCase(
                            craftsmanId = screenState.value.myOffersCraftsmanUiState.craftsManId,
                            userId = job.customerPhone,
                            requestId = job.id,
                            earnings = job.offer?.price ?: 0.0
                        )
                    }
                incrementJob.await()
                updateEarningsJob.await()
            },
            onSuccess = {
                onMarkAsDoneSuccess(job.title, job.customerPhone)
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
                    errorMessage = UiText.StringResource(R.string.error_marking_request_as_done)
                )
            )
            hideSnackBar()
        }
    }

    private fun onMarkAsDoneSuccess(requestTitle: String, customerId: String) {
        tryToExecute(
            execute = {
                addNotificationUseCase(
                    customerId,
                    NotificationToSend(
                        title = mapOf(
                            "en" to "Request Completed",
                            "ar" to "تم الانتهاء من الطلب"
                        ),
                        caption = mapOf(
                            "en" to "Your request ${requestTitle} has been marked as done.",
                            "ar" to "تم الانتهاء من طلبك '${requestTitle}'"
                        ),
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
                    errorMessage = UiText.StringResource(R.string.some_error_happened)
                )
            )
            hideSnackBar()
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
                    errorMessage = UiText.StringResource(R.string.occurred_while_sending_message_to_customer)
                )
            )
            hideSnackBar()
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

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBarError) {
                delay(3000)
                updateState(screenState.value.copy(showSnackBarError = false))
            }
        }
    }

    private companion object {
        const val TIMEOUT = 600L
    }
}