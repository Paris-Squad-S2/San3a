package com.paris_2.san3a.presentation.screen.home.customer

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetMostRequestedServicesUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.RequestServiceUseCase
import com.paris_2.san3a.domain.usecase.UpdateNumOfRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CustomerHomeViewModel(
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val getMostRequestedServicesUseCase: GetMostRequestedServicesUseCase,
    private val requestServicesUseCase: RequestServiceUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val updateNumOfRequestsUseCase: UpdateNumOfRequestsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
): CustomerHomeInteractionListener, BaseViewModel<CustomerHomeUiState>(CustomerHomeUiState()) {

    init{
        loadUserData()
        loadMostRequestedServices()
        loadServices()
    }

    override fun createRequest(service: RequestServiceUiState , serviceId: String) {
        tryToExecute(
            execute ={
                requestServicesUseCase(service.toRequestService())
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        bottomSheetState = false
                    )
                )
                updateNumOfRequests(serviceId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
                Log.e("CustomerHomeViewModel", it.message ?: "Unknown Error")
            }
        )
    }

    override fun updateNumOfRequests(serviceId: String) {
        tryToExecute(
            execute = { updateNumOfRequestsUseCase(serviceId) },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
                Log.e("CustomerHomeViewModel", it.message ?: "Unknown Error")
            }
        )
    }

    private fun getGovernments() {
        viewModelScope.launch {
            val governments = getLocationInfoUseCase.getGovernments(countryName = "Egypt")
            val firstGov = governments.names.firstOrNull() ?: ""

            updateState(
                screenState.value.copy(
                    customerUiState = screenState.value.customerUiState.copy(
                        locationUiState = screenState.value.customerUiState.locationUiState.copy(
                            government = firstGov
                        ),
                        government = firstGov
                    )
                )
            )
        }
    }

    fun getCities(stateName: String) {
        viewModelScope.launch {
            val cities = getLocationInfoUseCase.getCities(
                countryName = "Egypt",
                stateName = stateName
            )

            updateState(
                screenState.value.copy(
                    customerUiState = screenState.value.customerUiState.copy(
                        locationUiState = screenState.value.customerUiState.locationUiState.copy(
                            cities = cities.names
                        )
                    )
                )
            )
        }
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
                        errorMessage = it.message ?: "Unknown Error"
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
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    private fun loadUserData(){
        tryToExecute(
            execute = { getUserUseCase(getPhoneNumberUseCase()) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            id = it.id,
                            currentUserName = it.fullName,
                            government = it.location.government,
                            city = it.location.cityName,
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
                Log.e("CustomerHomeViewModel", it.message ?: "Unknown Error")
            }
        )
    }

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