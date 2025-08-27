package com.paris_2.san3a.presentation.screen.requests.customer

import android.util.Log
import com.paris_2.san3a.domain.entity.NotificationToSend
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.usecase.user.AddRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.services.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.user.GetCustomerRatingOnCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetRatingForCraftsmanUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messaging.CreateChatUseCase
import com.paris_2.san3a.domain.usecase.notification.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.domain.usecase.notification.SendNotificationUseCase
import com.paris_2.san3a.domain.usecase.requests.GetAcceptedOfferOnRequestUseCaseUseCase
import com.paris_2.san3a.domain.usecase.requests.GetOffersCountUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCustomerRequestsUseCase
import com.paris_2.san3a.domain.usecase.user.GetDeviceTokenUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.utill.roundFloat
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class MyRequestCustomerViewModel(
    private val getCustomerRequestsUseCase: GetCustomerRequestsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getOffersCountUseCase: GetOffersCountUseCase,
    private val getAcceptedOfferOnRequestUseCaseUseCase: GetAcceptedOfferOnRequestUseCaseUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getRatingForCraftsmanUseCase: GetRatingForCraftsmanUseCase,
    private val getCustomerRatingOnCraftsmanUseCase: GetCustomerRatingOnCraftsmanUseCase,
    private val addRatingForCraftsmanUseCase: AddRatingForCraftsmanUseCase,
    private val addNotificationUseCase: AddNotificationUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val getDeviceTokenUseCase: GetDeviceTokenUseCase,
    private val getUnReadNotificationsCountUseCase: GetUnReadNotificationsCountUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val getAllServicesUseCase: GetAllServicesUseCase,
) : BaseViewModel<MyRequestCustomerScreenState>(MyRequestCustomerScreenState()),
    MyRequestCustomerInteractionListener {

    init {
        getCustomerPhone()
    }

    private fun getUserServices() {
        tryToObserve(
            observe = { getAllServicesUseCase() },
            onEach = { servicesList ->
                updateState(
                    screenState.value.copy(
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                            services = servicesList.orEmpty().associateBy { it.id },
                        )
                    )
                )
                val servicesById = (servicesList.orEmpty()).associateBy { it.id }

                val current = screenState.value.myRequestCustomerUiState

                val updatedOngoing = current.ongoing.mapValues { (_, req) ->
                    req.copy(
                        serviceImage = servicesById[req.serviceId]?.imageUrl.orEmpty(),
                        serviceType = servicesById[req.serviceId]?.title.orEmpty()
                    )
                }
                val updatedCompleted = current.completed.mapValues { (_, req) ->
                    req.copy(
                        serviceImage = servicesById[req.serviceId]?.imageUrl.orEmpty(),
                        serviceType = servicesById[req.serviceId]?.title.orEmpty()
                    )
                }
                val updatedCanceled = current.canceled.mapValues { (_, req) ->
                    req.copy(
                        serviceImage = servicesById[req.serviceId]?.imageUrl.orEmpty(),
                        serviceType = servicesById[req.serviceId]?.title.orEmpty()
                    )
                }

                updateState(
                    screenState.value.copy(
                        myRequestCustomerUiState = current.copy(
                            services = servicesById,
                            ongoing = updatedOngoing,
                            completed = updatedCompleted,
                            canceled = updatedCanceled,
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = "Failed to fetch services",
                    )
                )
            }
        )
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
                            customerPhone = phoneNumber,
                        ),
                    )
                )
                getRequests()
                getNotificationsCount(phoneNumber)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = "Failed to fetch phone number",
                    )
                )
            }
        )
    }

    private fun getNotificationsCount(userId: String) {
        tryToObserve(
            observe = {
                getUnReadNotificationsCountUseCase(userId)
            },
            onEach = { count ->
                updateState(
                    screenState.value.copy(
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
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


    private fun getRequests() {
        tryToObserve(
            observe = {
                getCustomerRequestsUseCase(screenState.value.myRequestCustomerUiState.customerPhone)
            },
            onEach = { result ->
                updateState(
                    MyRequestCustomerScreenState(
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                            ongoing = result?.filter { it.requestStatus == RequestStatus.ONGOING }
                                ?.toRequestServiceUiStateMap(screenState.value.myRequestCustomerUiState.services)
                                ?: emptyMap(),
                            completed = result?.filter { it.requestStatus == RequestStatus.COMPLETED }
                                ?.toRequestServiceUiStateMap(screenState.value.myRequestCustomerUiState.services)
                                ?: emptyMap(),
                            canceled = result?.filter { it.requestStatus == RequestStatus.CANCELLED }
                                ?.toRequestServiceUiStateMap(screenState.value.myRequestCustomerUiState.services)
                                ?: emptyMap(),
                        ),
                    )
                )
                getOffersForRequests()
                getUserServices()
                getOffersCountForRequests(ListType.ONGOING)
                getOffersCountForRequests(ListType.COMPLETED)
                getOffersCountForRequests(ListType.CANCELED)
            },
            onError = {
                updateState(
                    MyRequestCustomerScreenState(
                        errorMessage = it.message,
                    )
                )
            }
        )
    }


    private fun getOffersCountForRequests(listType: ListType): List<Job> {
        val currentMap = when (listType) {
            ListType.ONGOING -> screenState.value.myRequestCustomerUiState.ongoing
            ListType.COMPLETED -> screenState.value.myRequestCustomerUiState.completed
            ListType.CANCELED -> screenState.value.myRequestCustomerUiState.canceled
        }
        if (currentMap.isEmpty()) return emptyList()

        return currentMap.map { (id, _) ->
            tryToExecute(
                execute = { getOffersCountUseCase(id).first() },
                onSuccess = { offersCount ->
                    val updatedRequests = when (listType) {
                        ListType.ONGOING -> screenState.value.myRequestCustomerUiState.ongoing.toMutableMap()
                        ListType.COMPLETED -> screenState.value.myRequestCustomerUiState.completed.toMutableMap()
                        ListType.CANCELED -> screenState.value.myRequestCustomerUiState.canceled.toMutableMap()
                    }
                    val request = updatedRequests[id]
                    if (request != null) {
                        updatedRequests[id] = request.copy(offersCount = offersCount)
                        val updatedUiState = when (listType) {
                            ListType.ONGOING -> screenState.value.myRequestCustomerUiState.copy(
                                ongoing = updatedRequests,
                            )

                            ListType.COMPLETED -> screenState.value.myRequestCustomerUiState.copy(
                                completed = updatedRequests,
                            )

                            ListType.CANCELED -> screenState.value.myRequestCustomerUiState.copy(
                                canceled = updatedRequests,
                            )
                        }
                        updateState(screenState.value.copy(myRequestCustomerUiState = updatedUiState))
                    }
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
                delay(TIMEOUT)
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                    )
                )
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
                    myRequestCustomerUiState = updatedState,
                )
            )

            getCraftsManDetails(
                requestId = requestId,
                craftsManId = offer.craftsmanId,
                listType = listType
            )

            delay(TIMEOUT)
            updateState(
                screenState.value.copy(
                    isLoading = false,
                )
            )

        },
        onError = {
            Log.e(
                "MyOfferCraftsmanViewModel",
                "Error fetching offer for request $requestId: ${it.message}"
            )
            updateState(
                screenState.value.copy(
                    errorMessage = it.message ?: "Failed to load offers for request $requestId",
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
                val ratingDeferred =
                    scope.async { getRatingForCraftsmanUseCase(craftsManId).first() }

                craftsManDeferred.await() to ratingDeferred.await()
            },
            onSuccess = { (craftsMan, rating) ->
                Log.d("MyOfferCraftsmanViewModel", "Fetched craftsman details: $craftsMan")
                val updatedRequests = when (listType) {
                    ListType.ONGOING -> screenState.value.myRequestCustomerUiState.ongoing.toMutableMap()
                    ListType.COMPLETED -> screenState.value.myRequestCustomerUiState.completed.toMutableMap()
                    ListType.CANCELED -> screenState.value.myRequestCustomerUiState.canceled.toMutableMap()
                }

                updatedRequests[requestId]?.let { request ->
                    updatedRequests[requestId] = request.copy(
                        offer = request.offer.copy(
                            craftsMan = craftsMan.toCraftsManUiState(rating)
                        )
                    )
                } ?: return@tryToExecute

                when (listType) {
                    ListType.ONGOING -> updateState(
                        screenState.value.copy(
                            myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                                ongoing = updatedRequests,
                            ),
                        )
                    )

                    ListType.COMPLETED -> updateState(
                        screenState.value.copy(
                            myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                                completed = updatedRequests,
                            ),
                        )
                    )

                    ListType.CANCELED -> updateState(
                        screenState.value.copy(
                            myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                                canceled = updatedRequests,
                            ),
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
        updateState(
            screenState.value.copy(
                errorMessage = null,
                isLoading = true,
            )
        )
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
                        errorMessage = "Failed to create chat: ${it.message}",
                    )
                )
            }
        )
    }

    override fun onRatingClick(craftsmanId: String) {
        updateState(
            screenState.value.copy(
                myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                    isRatingVisible = true,
                    craftsmanToRate = craftsmanId,
                    rating = 0f
                )
            )
        )
        tryToExecute(
            execute = {
                getCustomerRatingOnCraftsmanUseCase(
                    craftsmanId = craftsmanId,
                    userId = screenState.value.myRequestCustomerUiState.customerPhone
                )
            },
            onSuccess = { rating ->
                rating?.let {
                    updateState(
                        screenState.value.copy(
                            myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                                rating = rating
                            )
                        )
                    )
                }
            },
            onError = {
                Log.d(
                    "MyRequestCustomerViewModel",
                    "Error fetching customer rating on craftsman: ${it.message}"
                )
            }
        )
    }

    override fun onRatingDismiss() {
        updateState(
            screenState.value.copy(
                myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                    isRatingVisible = false,
                    rating = 0f
                )
            )
        )
    }

    override fun onRatingChange(rating: Float) {
        updateState(
            screenState.value.copy(
                myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                    rating = rating
                )
            )
        )
        Log.d("MyRequestCustomerViewModel", "Rating changed to: $rating")
    }

    override fun onRatingCraftsMan() {
        tryToExecute(
            execute = {
                addRatingForCraftsmanUseCase(
                    userId = screenState.value.myRequestCustomerUiState.customerPhone,
                    craftsmanId = screenState.value.myRequestCustomerUiState.craftsmanToRate,
                    rating = screenState.value.myRequestCustomerUiState.rating
                )
            },
            onSuccess = {
                addNotificationUseCase(
                    screenState.value.myRequestCustomerUiState.craftsmanToRate,
                    NotificationToSend(
                        title = mapOf(
                            "en" to "You got a new Rating",
                            "ar" to "لقد حصلت على تقييم جديد"
                        ),
                        caption = mapOf(
                            "en" to "You have been rated by ${screenState.value.myRequestCustomerUiState.rating.roundFloat()} by a customer.",
                            "ar" to "لقد تم تقييمك بـ ${screenState.value.myRequestCustomerUiState.rating.roundFloat()} من قبل عميل."
                        ),
                    )
                )
                sendNotificationUseCase(
                    token = getDeviceTokenUseCase(screenState.value.myRequestCustomerUiState.customerPhone),
                    title = "You got a new Rating",
                    description = "You have been rated by ${screenState.value.myRequestCustomerUiState.rating}"
                )
                updateState(
                    screenState.value.copy(
                        myRequestCustomerUiState = screenState.value.myRequestCustomerUiState.copy(
                            isRatingVisible = false,
                            rating = 0f,
                            craftsmanToRate = ""
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = "Failed to fetch craftsman details: ${it.message}",
                    )
                )
            }
        )
    }

    private companion object {
        const val TIMEOUT = 600L
    }
}