package com.paris_2.san3a.presentation.screen.requests.craftsman

import android.util.Log
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.usecase.AddNotificationUseCase
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
            onSuccess = { phoneNumber: String ->
                updateState(
                    screenState.value.copy(
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            craftsManId = phoneNumber
                        )
                    )
                )
                getCraftsManOfferOnRequest()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = "Failed to fetch phone number"
                    )
                )
            }
        )
    }

    private fun getCraftsManOfferOnRequest() {
        tryToObserve(
            observe = {
                getCraftsManRequestsUseCase(screenState.value.myOffersCraftsmanUiState.craftsManId)
            },
            onEach = { result ->
                Log.d("MyOfferCraftsmanViewModel", "Fetched requests: $result")
                val filteredResult = result.filter { it.selectedCraftsmanId.isNullOrBlank() || it.selectedCraftsmanId == screenState.value.myOffersCraftsmanUiState.craftsManId }
                Log.d("MyOfferCraftsmanViewModel", "Filtered requests: $filteredResult")
                updateState(
                    MyJobsCraftsmanScreenState(
                        isLoading = false,
                        myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(
                            ongoing = filteredResult.filter { it.requestStatus == RequestStatus.ONGOING }.toMyJobOfferUiStateMap(),
                            completed = filteredResult.filter { it.requestStatus == RequestStatus.COMPLETED }.toMyJobOfferUiStateMap(),
                            canceled = filteredResult.filter { it.requestStatus == RequestStatus.CANCELLED }.toMyJobOfferUiStateMap()
                        )
                    )
                )
                getOffersForRequests()
            },
            onError = {
                updateState(
                    MyJobsCraftsmanScreenState(
                        isLoading = false,
                        errorMessage = it.message
                    )
                )
            }
        )
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
            onError = {
                Log.e("MyOfferCraftsmanViewModel", "Error fetching offers: ${it.message}")
            }
        )
    }

    enum class ListType {
        ONGOING, COMPLETED, CANCELED
    }
    private fun updateRequestOffer(requestId: String, listType: ListType) = tryToObserve(
        observe = {
            getCraftManOfferOnRequestUseCase(
                requestId = requestId,
                craftsManId = screenState.value.myOffersCraftsmanUiState.craftsManId
            )
        },
        onEach = { offer ->
            if (offer == null) {
                Log.d("MyOfferCraftsmanViewModel", "No offer found for request $requestId")
                return@tryToObserve
            }

            val updatedRequests = when(listType) {
                ListType.ONGOING -> screenState.value.myOffersCraftsmanUiState.ongoing.toMutableMap()
                ListType.COMPLETED -> screenState.value.myOffersCraftsmanUiState.completed.toMutableMap()
                ListType.CANCELED -> screenState.value.myOffersCraftsmanUiState.canceled.toMutableMap()
            }

            Log.d("MyOfferCraftsmanViewModel", "Updating request $requestId with offer: $offer")

            updatedRequests[requestId] = updatedRequests[requestId]?.copy(
                offer = offer.toUiState()
            ) ?: return@tryToObserve

            Log.d("MyOfferCraftsmanViewModel", "Updated request $requestId: ${updatedRequests[requestId]}")

            val updatedState = when (listType) {
                ListType.ONGOING -> screenState.value.myOffersCraftsmanUiState.copy(ongoing = updatedRequests)
                ListType.COMPLETED -> screenState.value.myOffersCraftsmanUiState.copy(completed = updatedRequests)
                ListType.CANCELED -> screenState.value.myOffersCraftsmanUiState.copy(canceled = updatedRequests)
            }

            updateState(
                screenState.value.copy(
                    myOffersCraftsmanUiState = updatedState
                )
            )

            getCraftsManDetails(requestId = requestId, craftsManId = offer.craftsmanId, listType = listType)

        },
        onError = {
            Log.e("MyOfferCraftsmanViewModel", "Error fetching offer for request $requestId: ${it.message}")
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    errorMessage = it.message ?: "Failed to load offers for request $requestId"
                )
            )
        },
    )

    private fun getCraftsManDetails(
        requestId: String,
        craftsManId: String,
        listType: ListType
    ) {
        tryToExecute(
            execute = { scope ->
                val craftsManDeferred = scope.async { getUserUseCase(craftsManId) }
                val ratingDeferred = scope.async { getRatingForCraftsmanUseCase(craftsManId).first() }

                craftsManDeferred.await() to ratingDeferred.await()
            },
            onSuccess = { (craftsMan, rating) ->
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
                ) ?: return@tryToExecute

                when (listType) {
                    ListType.ONGOING -> updateState(
                        screenState.value.copy(
                            myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(ongoing = updatedRequests)
                        )
                    )
                    ListType.COMPLETED -> updateState(
                        screenState.value.copy(
                            myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(completed = updatedRequests)
                        )
                    )
                    ListType.CANCELED -> updateState(
                        screenState.value.copy(
                            myOffersCraftsmanUiState = screenState.value.myOffersCraftsmanUiState.copy(canceled = updatedRequests)
                        )
                    )
                }
            },
            onError = {
                Log.e("MyOfferCraftsmanViewModel", "Error fetching craftsman details: ${it.message}")
            }
        )
    }


    override fun onMarkAsDone(requestId: String, requestTitle: String, customerId: String) {
        tryToExecute(
            execute = {
                markRequestAsDoneUseCase(requestId)
            },
            onSuccess = {
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
            onError = {
                //todo show snack bar
            }
        )
    }

    override fun onSendMessageClick(phoneNumber: String) {
        tryToExecute(
            execute = {
                createChatUseCase(
                    listOf(screenState.value.myOffersCraftsmanUiState.craftsManId, phoneNumber)
                )
            },
            onSuccess = { chatId ->
                navigate(
                    Destinations.MessageDetails(
                        chatId = chatId,
                        currentUserId = screenState.value.myOffersCraftsmanUiState.craftsManId,
                        otherUserId = phoneNumber
                    )
                )
            },
            onError = {
                //todo show snack bar
            }
        )
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
        getCraftsManPhone()
    }
}