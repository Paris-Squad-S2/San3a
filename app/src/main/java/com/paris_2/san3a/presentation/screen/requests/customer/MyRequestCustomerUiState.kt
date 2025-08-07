package com.paris_2.san3a.presentation.screen.requests.customer

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus


data class MyRequestCustomerScreenState(
    val isLoading: Boolean = false,
    val myRequestCustomerUiState: MyRequestCustomerUiState = MyRequestCustomerUiState(),
    val errorMessage: String? = null
)

data class MyRequestCustomerUiState(
    val customerPhone: String = "",
    val ongoing: List<MyRequestCustomerUi> = emptyList(),
    val completed: List<MyRequestCustomerUi> = emptyList(),
    val canceled: List<MyRequestCustomerUi> = emptyList()
)

fun RequestService.toRequestServiceUiState(): MyRequestCustomerUi { //TODO
    return MyRequestCustomerUi(
        requestTitle = this.title,
        serviceType = this.serviceType,
        numberOfOffers = this.offers.size,
        date = this.time.date.toString(),
        time = this.time.time.toString(),
    )
}

fun List<RequestService>.toRequestServiceUiStateList(): List<MyRequestCustomerUi> {
    return this.map { it.toRequestServiceUiState() }
}

data class MyRequestCustomerUi(
    val requestTitle: String = "My Requests",
    val serviceType: String = "Service Request",
    val numberOfOffers: Int = 0,
    val date: String = "2023-10-01",
    val time: String = "10:00 AM",
    val craftsmanName: String? = "John Doe",
    val craftsmanRating: Float? = 4.5f,
    val craftsmanURL: String? = null,
    val isCraftsmanVerified: Boolean = false,
    val isAcceptedOffer: Boolean = false,
    val isRated: Boolean = false,
    val status: RequestStatus = RequestStatus.ONGOING
)