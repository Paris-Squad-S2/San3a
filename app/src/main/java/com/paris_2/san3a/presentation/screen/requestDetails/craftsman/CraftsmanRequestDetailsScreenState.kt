package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.shared.components.OfferDetailsUIState
import com.paris_2.san3a.presentation.shared.components.OfferStatus
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import com.paris_2.san3a.presentation.utill.getTimeAgo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class CraftsmanRequestDetailsScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val uiState: CraftsmanRequestDetailsUiState = CraftsmanRequestDetailsUiState(),
)

data class CraftsmanRequestDetailsUiState(
    val request: RequestServiceUIState = RequestServiceUIState(),
    val offers: List<RequestOfferUiState> = emptyList(),
    val offersFromCraftsman: List<RequestOfferUiState> = emptyList(),
    val yourOffers: List<RequestOfferUiState> = emptyList(),
    val craftsmanRequestDetails: CraftsmanRequestDetails? = null,
    val acceptedOffer: RequestOfferUiState? = null,
    val customer: Customer = Customer(),
)

data class RequestOfferUiState(
    val id: String = "",
    val craftsmanId: String = "",
    val craftsmanImageUrl: String = "",
    val craftsmanName: String = "",
    val craftsmanRate: Float = 3.5f,
    val craftsmanReviewsNumber: Int = 150,
    val requestId: String = "",
    val price: String = "0.0",
    val time: String = "Tomorrow at 10:00 AM",
    val postedTime: String = "1 hour ago", //TODO
    val messageToCustomer: String = "",
    val isAccepted: Boolean = false,
)

fun Offer.toOfferUiState(): RequestOfferUiState {
    return RequestOfferUiState(
        id = this.id,
        craftsmanId = this.craftsmanId,
        requestId = this.requestId,
        price = this.price.toString(),
        time = "${this.preferredDate} ${this.preferredTime}",
        postedTime = "1 hour ago", // TODO: Replace with actual time logic
        messageToCustomer = this.messageToCustomer,
        isAccepted = this.isAccepted
    )
}

fun RequestOfferUiState.toOfferDetailsUIState(yourOfferAccepted: Boolean = false): OfferDetailsUIState {
    return OfferDetailsUIState(
        imageUrl = this.craftsmanImageUrl,
        name = this.craftsmanName,
        rate = this.craftsmanRate,
        reviewsNumber = this.craftsmanReviewsNumber,
        description = this.messageToCustomer,
        amount = this.price,
        time = this.time,
        postedTime = this.postedTime,
        status = if (yourOfferAccepted) OfferStatus.OFFER_ACCEPTED else if (this.isAccepted) OfferStatus.YOUR_ACCEPTED_OFFER else OfferStatus.PENDING_OFFER,
        isVerify = false
    )
}

fun List<Offer>.toOfferUiStateList(): List<RequestOfferUiState> {
    return this.map { it.toOfferUiState() }
}

data class RequestServiceUIState(
    val id: String = "",
    val userId: String = "",
    val requestStatus: RequestStatus = RequestStatus.ONGOING,
    val title: String = "Loading...",
    val serviceType: String = "Loading...",
    val description: String = "Loading...",
    val location: String = "Loading...",
    val locationDetails: String = "Loading...",
    val time: String = "Loading...",
    val state: String = "Loading...",
    val images: List<String> = emptyList(),
    val offers: List<OfferUiState> = emptyList(),
    val selectedCraftsmanId: String? = null,
)

fun RequestService.toRequestServiceUIState(): RequestServiceUIState {
    return RequestServiceUIState(
        id = this.id,
        userId = this.userId,
        requestStatus = this.requestStatus,
        title = this.title,
        serviceType = this.serviceType,
        description = this.description,
        location = this.location,
        locationDetails = this.locationDetails,
        time = getTimeAgo(this.time),
        state = this.state,
        images = this.image,
        selectedCraftsmanId = this.selectedCraftsmanId
    )
}



fun RequestServiceUIState.toRequestService(): RequestService {
    return RequestService(
        id = this.id,
        userId = this.userId,
        requestStatus = this.requestStatus,
        title = this.title,
        serviceType = this.serviceType,
        description = this.description,
        location = this.location,
        locationDetails = this.locationDetails,
        time = getCurrentDateTime(),
        state = this.state,
        image = this.images,
        offers = listOf(),
        selectedCraftsmanId = this.selectedCraftsmanId
    )
}

data class Customer(
    val id: String = "",
    val name: String = "",
    val profilePhoto: String = ""
)

data class CraftsmanRequestDetails(
    val requestId: String = "",
    val title: String = "",
    val description: String = "",
    val serviceType: String = "",
    val time: String = "",
    val location: String = "",
    val photos: List<String> = emptyList(),
)

data class OfferUiState(
    val id: String = "",
    val craftsmanId: String = "",
    val requestId: String = "",
    val price: Double = 0.0,
    val preferredDate: LocalDate = LocalDate(1970, 1, 1),
    val preferredTime: LocalTime = LocalTime(0, 0),
    val messageToCustomer: String = "",
    val isAccepted: Boolean = false,
)

fun OfferUiState.toEntity() = Offer(
    id = this.id,
    requestId = this.requestId,
    craftsmanId = this.craftsmanId,
    price = this.price,
    preferredDate = this.preferredDate,
    preferredTime = this.preferredTime,
    messageToCustomer = this.messageToCustomer,
    isAccepted = this.isAccepted
)

val offerList = listOf(
    RequestOfferUiState(
        id = "1",
        craftsmanId = "c1",
        craftsmanImageUrl = "https://example.com/image1.jpg",
        craftsmanName = "John Doe",
        craftsmanRate = 4.5f,
        craftsmanReviewsNumber = 200,
        requestId = "r1",
        price = "150.0",
        time = "Tomorrow at 10:00 AM",
        postedTime = "2 hours ago",
        messageToCustomer = "Looking forward to working with you!",
        isAccepted = false
    ),
    RequestOfferUiState(
        id = "2",
        craftsmanId = "+201118295474",
        craftsmanImageUrl = "https://example.com/image2.jpg",
        craftsmanName = "Jane Smith",
        craftsmanRate = 4.8f,
        craftsmanReviewsNumber = 180,
        requestId = "r1",
        price = "170.0",
        time = "Tomorrow at 11:00 AM",
        postedTime = "1 hour ago",
        messageToCustomer = "I can start right away.",
        isAccepted = true
    )
)