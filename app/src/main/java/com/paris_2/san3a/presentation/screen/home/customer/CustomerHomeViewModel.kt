package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetMostRequestedServicesUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.flow.first

class CustomerHomeViewModel(
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val getMostRequestedServicesUseCase: GetMostRequestedServicesUseCase
): CustomerHomeInteractionListener, BaseViewModel<CustomerHomeUiState>(CustomerHomeUiState()) {

    init{
        loadUserData()
        loadMostRequestedServices()
        loadServices()
    }
    private fun loadServices(){
        tryToExecute(
            execute = { getAllServicesUseCase().first() },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            services = it
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    private fun loadMostRequestedServices(){
        tryToExecute(
            execute = { getMostRequestedServicesUseCase().first() },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            mostRequestedServices = it
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

    private fun loadUserData(){}

    override fun onNotificationClick() {
        navigate(Destinations.Notifications)
    }

    override fun onSearch(query: String) {}

    override fun onServiceClick(serviceId: String) {
        updateState(
            screenState.value.copy(
                bottomSheetState = true
            )
        )
    }

    override fun onBecomeCraftsmanClick() {
        navigate(Destinations.More)
    }

    override fun onDismissBottomSheet() {
        updateState(
            screenState.value.copy(
                bottomSheetState = false
            )
        )
    }

}