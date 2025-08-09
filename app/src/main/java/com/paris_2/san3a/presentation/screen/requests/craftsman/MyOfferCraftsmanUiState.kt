package com.paris_2.san3a.presentation.screen.requests.craftsman

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus

data class MyOfferCraftsmanScreenState(
    val isLoading: Boolean = false,
    val myOffersCraftsmanUiState: MyOfferCraftsmanUiState = MyOfferCraftsmanUiState(),
    val errorMessage: String? = null,
)

data class MyOfferCraftsmanUiState(
    val customerPhone: String = "",
    val requests: Map<String, MyJobOfferUiState> = emptyMap(),
    val ongoing: List<MyJobOfferUiState> = emptyList(),
    val completed: List<MyJobOfferUiState> = emptyList(),
    val canceled: List<MyJobOfferUiState> = emptyList(),
    val offers: List<OfferUiState> = emptyList(),
)

fun List<RequestService>.toMyJobOfferUiStateMap(): Map<String, MyJobOfferUiState> {
    return this.associateBy { it.id }.mapValues { it.value.toMyJobOfferUiState() }
}

data class MyJobOfferUiState(
    val customerPhone: String = "",
    val craftsManId: String = "",
    val jobOfferTitle: String = "",
    val serviceType: String = "",
    val date: String = "",
    val acceptedTime: String = "",
    val address: String = "",
    val craftsmanName: String? = "",
    val craftsmanRating: Float? = 0.0f,
    val reviewsNumber: Int? = 0,
    val craftsmanURL: String? = null,
    val isCraftsmanVerified: Boolean = false,
    val craftsmanMessages: String = "",
    val isApproved: Boolean = false,
    val craftsmanOfferPrice: Double = 0.0,
    val status: RequestStatus = RequestStatus.ONGOING,
)


fun RequestService.toMyJobOfferUiState(): MyJobOfferUiState {
    return MyJobOfferUiState(
        customerPhone = this.userId,
        jobOfferTitle = this.title,
        serviceType = this.serviceType,
        date = this.time.date.toString(),
        acceptedTime = this.time.time.toString(),
        craftsmanOfferPrice = this.offers.firstOrNull() ?: 0.0,
    )
}

data class OfferUiState(
    val id: String,
    val price: Double,
    val preferredDate: String,
    val preferredTime: String,
    val messageToCustomer: String,
    val isAccepted: Boolean,
)

fun Offer.toUiState(): OfferUiState {
    return OfferUiState(
        id = id,
        price = price,
        preferredDate = preferredDate.toString(),
        preferredTime = preferredTime.toString(),
        messageToCustomer = messageToCustomer,
        isAccepted = isAccepted
    )
}

fun List<Offer>.toUiStateList(): List<OfferUiState> = map { it.toUiState() }

fun List<RequestService>.toMyJobOfferUiStateList(): List<MyJobOfferUiState> {
    return this.map { it.toMyJobOfferUiState() }
}

