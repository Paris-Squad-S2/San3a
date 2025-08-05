package com.paris_2.san3a.presentation.screen.home.craftsman

import com.paris_2.san3a.domain.usecase.GetAvailableJobsUseCase
import com.paris_2.san3a.domain.usecase.GetRecentRelatedJobsUseCase
import com.paris_2.san3a.domain.usecase.GetStatsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.flow.first

class CraftsmanHomeViewModel(
    private val getStatsUseCase: GetStatsUseCase,
    private val getRecentRelatedJobsUseCase: GetRecentRelatedJobsUseCase,
    private val getAvailableJobsUseCase: GetAvailableJobsUseCase
) : CraftsmanInteractionListener, BaseViewModel<CraftsmanHomeState>(CraftsmanHomeState()) {

    init {
        loadUserData()
        loadStats("aIqNtF3KQ84r5x6jc9O1")
        loadRecentRelatedJobs("Plumbing")
        loadAvailableJobs()
    }

    private fun loadUserData() {}
    private fun loadStats(userId: String) {
        tryToExecute(
            execute = { getStatsUseCase(userId) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            stats = it ?: throw IllegalStateException("Stats cannot be null")
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

    fun loadRecentRelatedJobs(relatedJob: String) {
        tryToExecute(
            execute = { getRecentRelatedJobsUseCase(relatedJob).first() },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        craftsmanHomeUiState = screenState.value.craftsmanHomeUiState.copy(
                            recentRelatedJobs = it
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