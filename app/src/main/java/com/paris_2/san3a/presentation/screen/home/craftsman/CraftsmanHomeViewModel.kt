package com.paris_2.san3a.presentation.screen.home.craftsman

import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.location.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.domain.usecase.requests.GetAvailableJobsUseCase
import com.paris_2.san3a.domain.usecase.requests.GetOffersCountUseCase
import com.paris_2.san3a.domain.usecase.requests.GetRecentRelatedJobsUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetStatsUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CraftsmanHomeViewModel(
    private val getStatsUseCase: GetStatsUseCase,
    private val getRecentRelatedJobsUseCase: GetRecentRelatedJobsUseCase,
    private val getAvailableJobsUseCase: GetAvailableJobsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getOffersCountUseCase: GetOffersCountUseCase,
    private val getUserServicesUseCase: GetUserServicesUseCase,
    private val getUnReadNotificationsCountUseCase: GetUnReadNotificationsCountUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val getUserUseCase: GetUserUseCase,
) : CraftsmanInteractionListener, BaseViewModel<CraftsmanHomeState>(CraftsmanHomeState()) {

    init {
        loadPhoneNumber()
    }

    private fun getUserServices() {
        tryToObserve(
            observe = {
                getUserServicesUseCase(
                    screenState.value.craftsmanHomeUiState.phoneNumber,
                    isCraftsman = true
                )
            },
            onEach = { services ->
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            userServices = services.orEmpty().associateBy { service -> service.id }
                        )
                    )
                )
                loadAvailableJobs()
                loadRecentRelatedJobs()
            },
            onError = ::onError
        )
    }

    private fun loadPhoneNumber() {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = { phoneNumber ->
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            phoneNumber = phoneNumber,
                        )
                    )
                )
                loadUserDate()
                getNotificationsCount(phoneNumber)
                loadStats()
                getUserServices()
            },
            onError = ::onError
        )
    }

    private fun getNotificationsCount(userId: String) {
        tryToObserve(
            observe = { getUnReadNotificationsCountUseCase(userId) },
            onEach = { count ->
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            notificationsCount = count ?: 0
                        )
                    )
                )
            },
            onError = ::onError,
        )
    }

    private fun loadUserDate() {
        tryToExecute(
            execute = { getUserUseCase(screenState.value.craftsmanHomeUiState.phoneNumber) },
            onSuccess = ::onLoadUserDateSuccess,
            onError = ::onError
        )
    }

    private suspend fun onLoadUserDateSuccess(user: User) {
        val government =
            getLocationInfoUseCase.getGovernorateById(user.location.governmentId)
        val city = getLocationInfoUseCase.getCityById(user.location.cityId)
        updateState(
            screenState.value.copy(
                craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                    currentUserName = user.fullName,
                    location = listOfNotNull(
                        government?.name,
                        city?.name
                    ).joinToString(", "),
                )
            )
        )
    }

    private fun loadStats() {
        tryToObserve(
            observe = { getStatsUseCase(screenState.value.craftsmanHomeUiState.phoneNumber) },
            onEach = {
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            stats = it?.toStatsUiState() ?: StatsUiState()
                        )
                    )
                )
            },
            onError = ::onError
        )
    }

    fun loadRecentRelatedJobs() {
        tryToObserve(
            observe = { getRecentRelatedJobsUseCase(screenState.value.craftsmanHomeUiState.userServices.keys.toList(), screenState.value.craftsmanHomeUiState.phoneNumber) },
            onEach = { jobs ->
                jobs?.map { job ->
                    val governorate = getLocationInfoUseCase.getGovernorateById(job.governorateId)
                    val city = getLocationInfoUseCase.getCityById(job.cityId)
                    job.toRequestServiceUiState(
                        location = listOfNotNull(
                            governorate?.name,
                            city?.name
                        ).joinToString(", "),
                        imageUrl = screenState.value.craftsmanHomeUiState.userServices[job.serviceId]?.imageUrl.orEmpty(),
                        serviceType = screenState.value.craftsmanHomeUiState.userServices[job.serviceId]?.title.orEmpty()
                    )
                }.also { mappedJobs ->
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                recentRelatedJobs = mappedJobs?.associate { requestService ->
                                    requestService.id to requestService
                                }.orEmpty()
                            )
                        )
                    )
                    getOffersCountForRecentJobs()
                }
            },
            onError = ::onError
        )
    }

    private fun getOffersCountForRecentJobs() {
        val recentJobs = screenState.value.craftsmanHomeUiState.recentRelatedJobs
        if (recentJobs.isEmpty()) return

        recentJobs.forEach { id, job ->
            tryToObserve(
                observe = { getOffersCountUseCase(id) },
                onEach = { offersCount ->
                    val updatedJob = job.copy(offersCount = offersCount ?: 0)
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                recentRelatedJobs = screenState.value.craftsmanHomeUiState.recentRelatedJobs.toMutableMap()
                                    .apply {
                                        this[id] = updatedJob
                                    }
                            )
                        )
                    )
                },
                onError = ::onError
            )
        }
    }

    fun loadAvailableJobs() {
        tryToObserve(
            observe = { getAvailableJobsUseCase(screenState.value.craftsmanHomeUiState.phoneNumber) },
            onEach = { jobs ->
                jobs?.map { job ->
                    val governorate = getLocationInfoUseCase.getGovernorateById(job.governorateId)
                    val city = getLocationInfoUseCase.getCityById(job.cityId)
                    job.toRequestServiceUiState(
                        location = listOfNotNull(
                            governorate?.name,
                            city?.name
                        ).joinToString(", "),
                        imageUrl = screenState.value.craftsmanHomeUiState.userServices[job.serviceId]?.imageUrl.orEmpty(),
                        serviceType = screenState.value.craftsmanHomeUiState.userServices[job.serviceId]?.title.orEmpty()
                    )
                }.also { mappedJobs ->
                    val services = screenState.value.craftsmanHomeUiState.userServices.keys.toList()
                    val filteredJobs =
                        mappedJobs?.filter { service -> services.contains(service.serviceId) }
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                availableJobs = filteredJobs?.associate { requestService ->
                                    requestService.id to requestService
                                } ?: emptyMap()
                            )
                        )
                    )
                    getOffersCountForAvailableJobs()
                }
            },
            onError = ::onError
        )
    }

    private fun getOffersCountForAvailableJobs() {
        val availableJobs = screenState.value.craftsmanHomeUiState.availableJobs
        if (availableJobs.isEmpty()) return

        availableJobs.forEach { id, job ->
            tryToObserve(
                observe = { getOffersCountUseCase(id) },
                onEach = { offersCount ->
                    val updatedJob = job.copy(offersCount = offersCount ?: 0)
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                availableJobs = screenState.value.craftsmanHomeUiState.availableJobs.toMutableMap()
                                    .apply {
                                        this[id] = updatedJob
                                    }
                            )
                        )
                    )
                },
                onError = ::onError
            )
        }
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onJobClick(serviceId: String) {
        navigate(
            Destinations.RequestDetails(
                requestId = serviceId,
                phoneNumber = screenState.value.craftsmanHomeUiState.phoneNumber
            )
        )
    }

    private fun onError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = throwable.message ?: UNKNOWN_ERROR
            )
        )
    }

    private companion object{
        const val UNKNOWN_ERROR = "Unknown Error"
    }
}