package com.paris_2.san3a.presentation.screen.requestDetails.customer

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestDetailsStatus

data class CustomerRequestUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val customerRequestDetails: CustomerRequestDetails? = null,
    val offers: List<Offer> = emptyList(),
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
