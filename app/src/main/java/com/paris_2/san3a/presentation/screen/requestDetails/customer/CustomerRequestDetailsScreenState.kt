package com.paris_2.san3a.presentation.screen.requestDetails.customer

import com.paris_2.san3a.domain.entity.RequestDetailsStatus
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.RequestOfferUiState
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.RequestServiceUIState

data class CustomerRequestDetailsScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val uiState: CustomerRequestDetailsUiState = CustomerRequestDetailsUiState(),
)

data class CustomerRequestDetailsUiState(
    val request: RequestServiceUIState = RequestServiceUIState(),
    val offers: Map<String, RequestOfferUiState> = emptyMap(),
)

data class CustomerRequestDetails(
    val requestId: String = "",
    val title: String = "",
    val description: String = "",
    val serviceType: String = "",
    val time: String = "",
    val location: String = "",
    val photos: List<String> = emptyList(),
    val status: RequestDetailsStatus = RequestDetailsStatus.SUBMITTED,
)
