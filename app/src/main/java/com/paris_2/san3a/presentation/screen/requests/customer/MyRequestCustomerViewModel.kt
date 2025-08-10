package com.paris_2.san3a.presentation.screen.requests.customer

import android.util.Log
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetAcceptedOfferOnRequestUseCaseUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersCountUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCustomerRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll

class MyRequestCustomerViewModel(
    private val getCustomerRequestsUseCase: GetCustomerRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getOffersCountUseCase: GetOffersCountUseCase,
    private val getAcceptedOfferOnRequestUseCaseUseCase: GetAcceptedOfferOnRequestUseCaseUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createChatUseCase: CreateChatUseCase,
) : BaseViewModel<MyRequestCustomerScreenState>(MyRequestCustomerScreenState()),
    MyRequestCustomerInteractionListener {

    init {
        getCustomerPhone()
    }

    private fun getCustomerPhone() {
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
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                            customerPhone = phoneNumber
                        )
                    )
                )
                getRequests()
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

    private fun getRequests() {
        tryToObserve(
            observe = {
                getCustomerRequestsUseCase(screenState.value.myRequestCustomerUiState.customerPhone)
            },
            onEach = { result ->
                updateState(
                    MyRequestCustomerScreenState(
                        isLoading = false,
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                            ongoing = result.filter { it.requestStatus == RequestStatus.ONGOING }
                                .toRequestServiceUiStateMap(),
                            completed = result.filter { it.requestStatus == RequestStatus.COMPLETED }
                                .toRequestServiceUiStateMap(),
                            canceled = result.filter { it.requestStatus == RequestStatus.CANCELLED }
                                .toRequestServiceUiStateMap()
                        )
                    )
                )
                getOffersCountForOngoing()
                getOffersCountForCompleted()
                getOffersCountForCanceled()
                getOffersForRequests()
            },
            onError = {
                updateState(
                    MyRequestCustomerScreenState(
                        isLoading = false,
                        errorMessage = it.message
                    )
                )
            }
        )
    }

    fun getOffersCountForOngoing() =
        getOffersCountForRequests(
            screenState.value.myRequestCustomerUiState.ongoing
        ) { updatedMap ->
            updateState(
                screenState.value.copy(
                    myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                        ongoing = updatedMap
                    )
                )
            )
        }

    fun getOffersCountForCompleted() =
        getOffersCountForRequests(
            screenState.value.myRequestCustomerUiState.completed
        ) { updatedMap ->
            updateState(
                screenState.value.copy(
                    myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                        completed = updatedMap
                    )
                )
            )
        }


    fun getOffersCountForCanceled() =
        getOffersCountForRequests(
            screenState.value.myRequestCustomerUiState.canceled
        ) { updatedMap ->
            updateState(
                screenState.value.copy(
                    myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                        canceled = updatedMap
                    )
                )
            )
        }

    private fun getOffersCountForRequests(
        list: Map<String, MyRequestCustomerUi>,
        updateList: (Map<String, MyRequestCustomerUi>) -> Unit
    ) : List<Job> {
        if (list.isEmpty()) return emptyList()

        return list.map { (id, request) ->
            tryToExecute(
                execute = { getOffersCountUseCase(id).first() },
                onSuccess = { offersCount ->
                    val updatedRequest = request.copy(offersCount = offersCount)
                    val updatedMap = list.toMutableMap().apply { this[id] = updatedRequest }
                    updateList(updatedMap)
                },
                onError = {
                    Log.e("MyRequestCustomerViewModel", "Error loading offers count: ${it.message}")
                }
            )
        }
    }

    private fun getOffersForRequests() = tryToExecute(
        execute = {
            screenState.value.myRequestCustomerUiState.ongoing.map { mapEntry ->
                updateRequestOffer(requestId = mapEntry.key, listType = ListType.ONGOING)
            }
            screenState.value.myRequestCustomerUiState.completed.map { mapEntry ->
                updateRequestOffer(requestId = mapEntry.key, listType = ListType.COMPLETED)
            }
            screenState.value.myRequestCustomerUiState.canceled.map { mapEntry ->
                updateRequestOffer(requestId = mapEntry.key, listType = ListType.CANCELED)
            }
        },
        onError = {
            Log.e("MyOfferCraftsmanViewModel", "Error fetching offers: ${it.message}")
        }
    )

    enum class ListType {
        ONGOING, COMPLETED, CANCELED
    }

    private fun updateRequestOffer(requestId: String, listType: ListType) = tryToObserve(
        observe = {
            getAcceptedOfferOnRequestUseCaseUseCase(
                requestId = requestId,
            )
        },
        onEach = { offer ->
            if (offer == null) {
                Log.d("MyOfferCraftsmanViewModel", "No offer found for request $requestId")
                return@tryToObserve
            }

            val updatedRequests = when (listType) {
                ListType.ONGOING -> screenState.value.myRequestCustomerUiState.ongoing.toMutableMap()
                ListType.COMPLETED -> screenState.value.myRequestCustomerUiState.completed.toMutableMap()
                ListType.CANCELED -> screenState.value.myRequestCustomerUiState.canceled.toMutableMap()
            }

            Log.d("MyOfferCraftsmanViewModel", "Updating request $requestId with offer: $offer")

            updatedRequests[requestId] = updatedRequests[requestId]?.copy(
                offer = offer.toUiState()
            ) ?: return@tryToObserve

            Log.d(
                "MyOfferCraftsmanViewModel",
                "Updated request $requestId: ${updatedRequests[requestId]}"
            )

            val updatedState = when (listType) {
                ListType.ONGOING -> screenState.value.myRequestCustomerUiState.copy(ongoing = updatedRequests)
                ListType.COMPLETED -> screenState.value.myRequestCustomerUiState.copy(completed = updatedRequests)
                ListType.CANCELED -> screenState.value.myRequestCustomerUiState.copy(canceled = updatedRequests)
            }

            updateState(
                screenState.value.copy(
                    myRequestCustomerUiState = updatedState
                )
            )

            getCraftsManDetails(
                requestId = requestId,
                craftsManId = offer.craftsmanId,
                listType = listType
            )

        },
        onError = {
            Log.e(
                "MyOfferCraftsmanViewModel",
                "Error fetching offer for request $requestId: ${it.message}"
            )
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
            execute = {
                getUserUseCase(craftsManId)
            },
            onSuccess = { craftsMan ->
                Log.d("MyOfferCraftsmanViewModel", "Fetched craftsman details: $craftsMan")
                val updatedRequests = when (listType) {
                    ListType.ONGOING -> screenState.value.myRequestCustomerUiState.ongoing.toMutableMap()
                    ListType.COMPLETED -> screenState.value.myRequestCustomerUiState.completed.toMutableMap()
                    ListType.CANCELED -> screenState.value.myRequestCustomerUiState.canceled.toMutableMap()
                }

                updatedRequests[requestId]?.let { request ->
                    updatedRequests[requestId] = request.copy(
                        offer = request.offer.copy(
                            craftsMan = craftsMan.toCraftsManUiState()
                        )
                    )
                } ?: return@tryToExecute

                when (listType) {
                    ListType.ONGOING -> updateState(
                        screenState.value.copy(
                            myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                                ongoing = updatedRequests
                            )
                        )
                    )

                    ListType.COMPLETED -> updateState(
                        screenState.value.copy(
                            myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                                completed = updatedRequests
                            )
                        )
                    )

                    ListType.CANCELED -> updateState(
                        screenState.value.copy(
                            myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                                canceled = updatedRequests
                            )
                        )
                    )
                }
            },
            onError = {
                Log.e(
                    "MyOfferCraftsmanViewModel",
                    "Error fetching craftsman details: ${it.message}"
                )
            }
        )
    }


    override fun onRequestClick(requestId: String) {
        navigate(
            Destinations.RequestDetails(
                requestId,
                phoneNumber = screenState.value.myRequestCustomerUiState.customerPhone
            )
        )
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onRetryClick() {
        getCustomerPhone()
    }

    override fun onClickChat(phoneNumber: String) {
        tryToExecute(
            execute = {
                createChatUseCase(
                    listOf(
                        phoneNumber,
                        screenState.value.myRequestCustomerUiState.customerPhone
                    )
                )
            },
            onSuccess = { chatId ->
                navigate(
                    Destinations.MessageDetails(
                        chatId = chatId,
                        currentUserId = screenState.value.myRequestCustomerUiState.customerPhone,
                        otherUserId = phoneNumber
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = "Failed to create chat: ${it.message}"
                    )
                )
            }
        )
    }
}