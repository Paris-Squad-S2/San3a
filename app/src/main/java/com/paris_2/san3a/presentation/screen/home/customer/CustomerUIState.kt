package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.MostRequestedServices
import com.paris_2.san3a.domain.entity.Service

data class CustomerHomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val customerUiState: CustomerUiState = CustomerUiState(),
    val bottomSheetState: Boolean = false
)

data class CustomerUiState(
    val currentUserName: String = "",
    val location: String = "",
    val mostRequestedServices: List<MostRequestedServices> = emptyList(),
    val services: List<Service> = emptyList(),
)
