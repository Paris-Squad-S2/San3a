package com.paris_2.san3a.presentation.screen.home.craftsman

import android.util.Log
import com.paris_2.san3a.domain.usecase.GetAvailableJobsUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetRecentRelatedJobsUseCase
import com.paris_2.san3a.domain.usecase.GetStatsUseCase
import com.paris_2.san3a.domain.usecase.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.requestDetails.GetOffersCountUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CraftsmanHomeViewModel(
    private val getStatsUseCase: GetStatsUseCase,
    private val getRecentRelatedJobsUseCase: GetRecentRelatedJobsUseCase,
    private val getAvailableJobsUseCase: GetAvailableJobsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getOffersCountUseCase: GetOffersCountUseCase,
    private val getUserServicesUseCase: GetUserServicesUseCase,
    private val getUserUseCase: GetUserUseCase,
) : CraftsmanInteractionListener, BaseViewModel<CraftsmanHomeState>(CraftsmanHomeState()) {

    init {
        loadPhoneNumber()
        loadAvailableJobs()
    }

    private fun getUserServices() {
        val currentLocale = "englishName"
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
                            userServices = services.map {
                                it.title[currentLocale] ?: it.title.values.firstOrNull() ?: ""
                            },
                        )
                    )
                )
                loadRecentRelatedJobs()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
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
                loadStats()
                getUserServices()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    private fun loadUserDate() {
        tryToExecute(
            execute = { getUserUseCase(screenState.value.craftsmanHomeUiState.phoneNumber) },
            onSuccess = { user ->
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            currentUserName = user.fullName,
                            location = user.location.government + ", " + user.location.cityName
                        )
                    )
                )
                Log.d("CraftsmanHomeViewModel", "User data loaded successfully: ${user.fullName}")
            },
            onError = {
                Log.e("CraftsmanHomeViewModel", "Error loading user data: ${it.message}")
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    private fun loadStats() {
        tryToObserve(
            observe = { getStatsUseCase(screenState.value.craftsmanHomeUiState.phoneNumber) },
            onEach = {
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            stats = it.toStatsUiState()
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    fun loadRecentRelatedJobs() {
        tryToObserve(
            observe = { getRecentRelatedJobsUseCase(screenState.value.craftsmanHomeUiState.userServices) },
            onEach = {
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            recentRelatedJobs = it.toRequestServiceUiStateMap()
                        )
                    )
                )
                getOffersCountForRecentJobs()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    private fun getOffersCountForRecentJobs() {
        val recentJobs = screenState.value.craftsmanHomeUiState.recentRelatedJobs
        if (recentJobs.isEmpty()) return

        recentJobs.forEach { id, job ->
            tryToObserve(
                observe = {
                    getOffersCountUseCase(id)
                },
                onEach = { offersCount ->
                    val updatedJob = job.copy(offersCount = offersCount)
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                recentRelatedJobs = screenState.value.craftsmanHomeUiState.recentRelatedJobs.toMutableMap().apply {
                                    this[id] = updatedJob
                                }
                            )
                        )
                    )
                },
                onError = {
                    Log.e("CraftsmanHomeViewModel", "Error loading offers count: ${it.message}")
                }
            )
        }
    }

    fun loadAvailableJobs() {
        tryToObserve(
            observe = getAvailableJobsUseCase::invoke,
            onEach = {
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            availableJobs = it.toRequestServiceUiStateMap()
                        )
                    )
                )
                getOffersCountForAvailableJobs()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    private fun getOffersCountForAvailableJobs() {
        val availableJobs = screenState.value.craftsmanHomeUiState.availableJobs
        if (availableJobs.isEmpty()) return

        availableJobs.forEach { id, job ->
            tryToObserve(
                observe = {
                    getOffersCountUseCase(id)
                },
                onEach = { offersCount ->
                    val updatedJob = job.copy(offersCount = offersCount)
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                availableJobs = screenState.value.craftsmanHomeUiState.availableJobs.toMutableMap().apply {
                                    this[id] = updatedJob
                                }
                            )
                        )
                    )
                },
                onError = {
                    Log.e("CraftsmanHomeViewModel", "Error loading offers count: ${it.message}")
                }
            )
        }
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onSearch(query: String) {}

    override fun onJobClick(serviceId: String) {
        navigate(
            Destinations.RequestDetails(
                requestId = serviceId,
                phoneNumber = screenState.value.craftsmanHomeUiState.phoneNumber
            )
        )
    }

}