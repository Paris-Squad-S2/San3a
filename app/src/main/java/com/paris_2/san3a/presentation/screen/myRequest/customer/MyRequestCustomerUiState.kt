package com.paris_2.san3a.presentation.screen.myRequest.customer

import com.paris_2.san3a.domain.entity.RequestStatus


sealed class MyRequestCustomerScreenState {
    object Loading : MyRequestCustomerScreenState()
    data class Success(
        val ongoing: List<MyRequestCustomerUi>,
        val completed: List<MyRequestCustomerUi>,
        val canceled: List<MyRequestCustomerUi>
    ) : MyRequestCustomerScreenState()
    data class Error(val message: String) : MyRequestCustomerScreenState()
}

data class MyRequestCustomerUi(
    val requestTitle : String = "My Requests",
    val serviceType : String = "Service Request",
    val numberOfOffers  : Int = 0,
    val date: String = "2023-10-01",
    val time: String = "10:00 AM",
    val craftsmanName : String? = "John Doe",
    val craftsmanRating : Float? = 4.5f,
    val craftsmanURL : String? = null,
    val isCraftsmanVerified : Boolean = false,
    val isAcceptedOffer: Boolean = false,
    val isRated: Boolean = false,
    val status: RequestStatus = RequestStatus.ONGOING
)