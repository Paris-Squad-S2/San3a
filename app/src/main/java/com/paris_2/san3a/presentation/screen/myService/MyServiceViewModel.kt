package com.paris_2.san3a.presentation.screen.myService

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.mapper.mapServiceToUiState
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.account.ServiceUiState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

data class MyServiceScreenState(
    val myServiceUiState: List<ServiceUiState> = emptyList(),
    val isLoading: Boolean = false,
    @StringRes val successMessageSnackBar: Int? = null,
    @StringRes val errorMessage: Int? = null,
    val showSnackBarSuccess: Boolean = false,
    val showSnackBarError: Boolean = false,
    val isNoInternet: Boolean = false,
    val phoneNumber: String = "",
    val isCraftsman: Boolean = false,
)


class MyServiceViewModel(
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getUserServicesUseCase: GetUserServicesUseCase,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<MyServiceScreenState>(MyServiceScreenState()),
    MyServiceInteractionListener {

    val phoneNumber = saveStateHandle.toRoute<Destinations.MyService>().phoneNumber
    val isCraftsman = saveStateHandle.toRoute<Destinations.MyService>().isCraftsman
    init {
        updateState(
            screenState.value.copy(
                phoneNumber = phoneNumber,
                isCraftsman = isCraftsman
            )
        )
        getAllServices()
    }

    private fun getUserSelectedServices() {
        updateState(screenState.value.copy(isLoading = true))
        tryToObserve(
            observe = {
                getUserServicesUseCase(
                    phoneNumber = phoneNumber,
                    isCraftsman = isCraftsman
                )
            },
            onEach = { services ->
                val serviceUiStates = mapServiceToUiState(services)
                updateState(
                    screenState.value.copy(
                        myServiceUiState = screenState.value.myServiceUiState.map { service ->
                            service.copy(isSelected = serviceUiStates.any { service.id == it.id })
                        },
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = R.string.error_occurred_while_fetching_services,
                        isLoading = false,
                        showSnackBarError = true
                    )
                )
            }
        )
    }

    private fun getAllServices() {
        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = { getAllServicesUseCase() },
            onSuccess = { services ->
                services.collect {
                    val serviceUiStates = mapServiceToUiState(it)
                    updateState(
                        screenState.value.copy(
                            myServiceUiState = serviceUiStates,
                        )
                    )
                getUserSelectedServices()
                }
            },
            onError = { throwable ->
                if (throwable is NoInternetConnectionException) {
                    updateState(
                        screenState.value.copy(
                            isNoInternet = true,
                            isLoading = false,
                            errorMessage = R.string.error_occurred_while_fetching_services,
                            showSnackBarError = false,
                            showSnackBarSuccess = false,
                            successMessageSnackBar = null
                        )
                    )
                } else {
                    updateState(
                        screenState.value.copy(
                            errorMessage = R.string.service_not_found,
                            isLoading = false,
                            showSnackBarError = true
                        )
                    )
                }

            },
        )
    }

    private fun uploadService() {
        tryToExecute(
            execute = {
                val currentLocale = "englishName"
                val selectedServices =
                    screenState.value.myServiceUiState.filter { it.isSelected }
                val isCraftsman = screenState.value.isCraftsman
                val services = selectedServices.map { serviceUiState ->
                    Service(
                        id = serviceUiState.id,
                        title = mapOf(currentLocale to serviceUiState.serviceTitle),
                        description = mapOf(currentLocale to serviceUiState.serviceDescription)
                    )
                }
                setUpAccountUseCase.saveServices(
                    phone = screenState.value.phoneNumber,
                    services,
                    isCraftsman = isCraftsman
                )
            },
            onSuccess = ::onUploadServiceSuccess,
            onError = ::onUploadServiceError
        )
    }

    private fun onUploadServiceSuccess(unit: Unit){
        updateState(
            screenState.value.copy(
                isLoading = false,
                showSnackBarSuccess = true,
                successMessageSnackBar = R.string.services_uploaded_successfully,
                showSnackBarError = false,
                errorMessage = null
            )
        )
    }

    private fun onUploadServiceError(throwable: Throwable) {
        if (throwable is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(
                    isNoInternet = true,
                    isLoading = false,
                    errorMessage = null,
                    showSnackBarError = false,
                    showSnackBarSuccess = false,
                    successMessageSnackBar = null
                )
            )
        } else {
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    errorMessage = R.string.occur_error_when_upload_service,
                    showSnackBarError = true
                )
            )
        }

    }

    override fun onBackClick() {
        navigateUp()
    }

    override fun onClickSave() {
        uploadService()
    }

    override fun onClickRetry() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                isNoInternet = false,
                errorMessage = null,
                showSnackBarError = false,
                showSnackBarSuccess = false,
                successMessageSnackBar = null
            )
        )
    }

    override fun onClickService(serviceId: String) {
        val updatedServices = screenState.value.myServiceUiState.map {
            if (it.id == serviceId) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        updateState(
            screenState.value.copy(
                myServiceUiState = updatedServices
            )
        )
    }

    override fun onDismissSnack() {
        updateState(
            screenState.value.copy(
                showSnackBarError = false,
                showSnackBarSuccess = false,
                errorMessage = null,
                successMessageSnackBar = null
            )
        )
    }

}