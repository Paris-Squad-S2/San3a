package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.presentation.utill.fakeImage
import com.paris_2.san3a.presentation.shared.components.OfferDetailsUIState
import com.paris_2.san3a.presentation.shared.components.OfferStatus
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class CraftsmanRequestDetailsScreenState(
    val isLoadingRequestDetails: Boolean = true,
    val isLoadingOffers: Boolean = true,
    val error: String? = null,
    val uiState: CraftsmanRequestDetailsUiState = CraftsmanRequestDetailsUiState(),
)

data class CraftsmanRequestDetailsUiState(
    val request: RequestServiceUIState = RequestServiceUIState(),
    val offerToAdd: OfferToAddUiState = OfferToAddUiState(),
    val offers: Map<String, RequestOfferUiState> = emptyMap(),
    val offersFromCraftsman: List<RequestOfferUiState> = emptyList(),
    val yourOffer: RequestOfferUiState? = null,
    val acceptedOffer: RequestOfferUiState? = null,
    val customer: Customer = Customer(),
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val isOfferValid: Boolean = false
)

data class OfferToAddUiState(
    val id: String = "",
    val price: String = "",
    val preferredDate: LocalDate? = null ,
    val preferredTime: LocalTime? = null,
    val messageToCustomer: String = "",
)

fun OfferToAddUiState.toOffer(craftsManId: String, requestId: String): Offer {
    return Offer(
        id = this.id,
        craftsmanId = craftsManId,
        requestId = requestId,
        price = this.price.toDoubleOrNull() ?: 0.0,
        preferredDate = this.preferredDate?: LocalDate(0, 0, 0),
        preferredTime = this.preferredTime?: LocalTime(0, 0),
        createdAt = getCurrentDateTime(),
        messageToCustomer = this.messageToCustomer,
        isAccepted = false,
    )
}

data class RequestOfferUiState(
    val id: String = "",
    val craftsmanId: String = "",
    val craftsmanImageUrl: String = "",
    val craftsmanName: String = "",
    val craftsmanRate: Float = 0f,
    val craftsmanReviewsNumber: Int = 150,
    val requestId: String = "",
    val price: Double = 0.0,
    val dateTime: LocalDateTime? = null,
    val postedTime: LocalDateTime?,
    val messageToCustomer: String = "",
    val isAccepted: Boolean = false,
)

fun Offer.toOfferUiState(): RequestOfferUiState {
    return RequestOfferUiState(
        id = this.id,
        craftsmanId = this.craftsmanId,
        requestId = this.requestId,
        price = this.price,
        dateTime = LocalDateTime(this.preferredDate, this.preferredTime),
        postedTime = this.createdAt,
        messageToCustomer = this.messageToCustomer,
        isAccepted = this.isAccepted
    )
}

fun User.toRequestOfferUiState(request: RequestOfferUiState, craftsmanRate: Float): RequestOfferUiState {
    return request.copy(
        craftsmanId = this.id,
        craftsmanImageUrl = this.profilePhoto ?: fakeImage,
        craftsmanName = this.fullName,
        craftsmanRate = craftsmanRate,
    )
}

fun RequestOfferUiState.toOfferDetailsUIState(offerAccepted: Boolean = false): OfferDetailsUIState {
    return OfferDetailsUIState(
        imageUrl = this.craftsmanImageUrl,
        name = this.craftsmanName,
        rate = this.craftsmanRate,
        reviewsNumber = this.craftsmanReviewsNumber,
        description = this.messageToCustomer,
        amount = this.price.toString(),
        dateTime = this.dateTime,
        postedTime = this.postedTime,
        status = if (offerAccepted) OfferStatus.YOUR_ACCEPTED_OFFER else if (this.isAccepted) OfferStatus.OFFER_ACCEPTED else OfferStatus.PENDING_OFFER,
        isVerify = false
    )
}

fun List<Offer>.toOfferUiStateMap(): Map<String, RequestOfferUiState> {
    return this.associateBy({ it.id }, { it.toOfferUiState() })
}

data class RequestServiceUIState(
    val id: String = "",
    val userId: String = "",
    val requestStatus: RequestStatus = RequestStatus.ONGOING,
    val title: String = "Loading...",
    val serviceType: String = "Loading...",
    val serviceImageUri: String = "",
    val description: String = "Loading...",
    val location: String = "Loading...",
    val locationDetails: String = "Loading...",
    val time: LocalDateTime? = null,
    val serviceId: String = "",
    val images: List<String> = emptyList(),
    val selectedCraftsmanId: String? = null,
)

fun RequestService.toRequestServiceUIState(location: String): RequestServiceUIState {
    return RequestServiceUIState(
        id = this.id,
        userId = this.userId,
        requestStatus = this.requestStatus,
        title = this.title,
        description = this.description,
        location = location,
        locationDetails = this.locationDetails,
        time = this.time,
        images = this.image,
        selectedCraftsmanId = this.selectedCraftsmanId
    )
}

data class Customer(
    val id: String = "",
    val name: String = "",
    val profilePhoto: String = ""
)
