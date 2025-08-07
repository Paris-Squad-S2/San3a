package com.paris_2.san3a.presentation.screen.requestDetails.customer

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestDetailsStatus
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.CraftsmanRequestDetailsUiState

data class CustomerRequestDetailsScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val customerRequestDetails: CustomerRequestDetails? = null,
    val offers: List<Offer> = emptyList(),
    val uiState: CraftsmanRequestDetailsUiState = CraftsmanRequestDetailsUiState(),
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
