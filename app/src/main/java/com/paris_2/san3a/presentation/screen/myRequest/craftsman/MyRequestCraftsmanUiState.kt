package com.paris_2.san3a.presentation.screen.myRequest.craftsman

import com.paris_2.san3a.domain.entity.RequestStatus


sealed class MyRequestCraftsmanScreenState {
    object Loading : MyRequestCraftsmanScreenState()
    data class Success(
        val ongoing: List<MyRequestCraftsmanUi>,
        val completed: List<MyRequestCraftsmanUi>,
        val canceled: List<MyRequestCraftsmanUi>
    ) : MyRequestCraftsmanScreenState()
    data class Error(val message: String) : MyRequestCraftsmanScreenState()
}

data class MyRequestCraftsmanUi(
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

