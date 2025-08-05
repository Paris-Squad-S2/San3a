package com.paris_2.san3a.presentation.screen.home.craftsman

import android.util.Log
import com.paris_2.san3a.domain.usecase.GetAvailableJobsUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetRecentRelatedJobsUseCase
import com.paris_2.san3a.domain.usecase.GetStatsUseCase
import com.paris_2.san3a.domain.usecase.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class CraftsmanHomeViewModel(
    private val getStatsUseCase: GetStatsUseCase,
    private val getRecentRelatedJobsUseCase: GetRecentRelatedJobsUseCase,
    private val getAvailableJobsUseCase: GetAvailableJobsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
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
            observe = { getUserServicesUseCase(screenState.value.craftsmanHomeUiState.phoneNumber, isCraftsman = true) },
            onEach = { services ->
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            userServices = services.map { it.title[currentLocale] ?: it.title.values.firstOrNull() ?: "" },
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
        tryToExecute(
            execute = { getStatsUseCase(screenState.value.craftsmanHomeUiState.phoneNumber) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            stats = it
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
        tryToExecute(
            execute = { getRecentRelatedJobsUseCase(screenState.value.craftsmanHomeUiState.userServices) },
            onSuccess = { relatedJobs ->
                relatedJobs.collect {
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                recentRelatedJobs = it
                            )
                        )
                    )
                }
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

    fun loadAvailableJobs() {
        tryToExecute(
            execute = getAvailableJobsUseCase::invoke,
            onSuccess = { availableJobs ->
                availableJobs.collect {
                    updateState(
                        screenState.value.copy(
                            craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                                availableJobs = it
                            )
                        )
                    )
                }
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

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onSearch(query: String) {}

    override fun onJobClick(serviceId: String) {
        // navigate to request details
    }

}