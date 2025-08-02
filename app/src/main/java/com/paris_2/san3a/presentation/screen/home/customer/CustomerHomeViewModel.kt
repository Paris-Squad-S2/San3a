package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetMostRequestedServicesUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.home.customer.CustomerHomeInteractionListener
import com.paris_2.san3a.presentation.screen.home.HomeState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.flow.first

class CustomerHomeViewModel(
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val getMostRequestedServicesUseCase: GetMostRequestedServicesUseCase
): CustomerHomeInteractionListener, BaseViewModel<HomeState>(HomeState()) {

    init{
        loadUserData()
        loadMostRequestedServices()
        loadServices()
    }
    fun loadServices(){
        tryToExecute(
            execute = { getAllServicesUseCase().first() },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customerHomeUiState = screenState.value.customerHomeUiState.copy(
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

    fun loadMostRequestedServices(){
        tryToExecute(
            execute = { getMostRequestedServicesUseCase().first() },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customerHomeUiState = screenState.value.customerHomeUiState.copy(
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

    fun loadUserData(){}

    override fun onNotificationClick() {
        navigate(Destinations.Notifications)
    }

    override fun onSearch(query: String) {}

    override fun onServiceClick(serviceId: String) {
        // open bottom sheet
    }

    override fun onBecomeCraftsmanClick() {
        navigate(Destinations.More)
    }

}