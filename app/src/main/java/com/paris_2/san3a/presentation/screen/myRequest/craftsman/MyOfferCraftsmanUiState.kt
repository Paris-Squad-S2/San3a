package com.paris_2.san3a.presentation.screen.myRequest.craftsman

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus

data class MyOfferCraftsmanScreenState(
    val isLoading: Boolean = false,
    val myOffersCraftsmanUiState: MyOfferCraftsmanUiState = MyOfferCraftsmanUiState(),
    val errorMessage: String? = null
)

data class MyOfferCraftsmanUiState(
    val customerPhone: String = "",
    val ongoing: List<MyJobOfferUiState> = emptyList(),
    val completed: List<MyJobOfferUiState> = emptyList(),
    val canceled: List<MyJobOfferUiState> = emptyList()
)

data class MyJobOfferUiState(
    val jobOfferTitle : String = "job Offer Title",
    val serviceType : String = "Service Request",
    val date: String = "2023-10-01",
    val acceptedTime: String = "10:00 AM",
    val address : String = "123 Main St, City, Country",
    val craftsmanName : String? = "John Doe",
    val craftsmanRating : Float? = 4.5f,
    val reviewsNumber : Int? = 100,
    val craftsmanURL : String? = null,
    val isCraftsmanVerified : Boolean = false,
    val craftsmanMessages : String = "Hello, I am available for your request.",
    val isApproved: Boolean = false,
    val craftsmanOfferPrice : Double = 53_000.0,
    val status: RequestStatus = RequestStatus.ONGOING
)

fun RequestService.toMyJobOfferUiState(): MyJobOfferUiState {
    return MyJobOfferUiState(
        jobOfferTitle = this.title,
        serviceType = this.serviceType,
        date = this.time.date.toString(),
        acceptedTime = this.time.time.toString(),
        craftsmanOfferPrice = this.offers.firstOrNull() ?: 0.0,
    )
}

fun List<RequestService>.toMyJobOfferUiStateList(): List<MyJobOfferUiState> {
    return this.map { it.toMyJobOfferUiState() }
}

