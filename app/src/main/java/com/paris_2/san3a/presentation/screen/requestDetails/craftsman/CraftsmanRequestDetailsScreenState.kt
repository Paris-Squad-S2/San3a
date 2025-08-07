package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import com.paris_2.san3a.presentation.utill.getTimeAgo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

data class CraftsmanRequestDetailsScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val uiState: CraftsmanRequestDetailsUiState = CraftsmanRequestDetailsUiState(),
)

data class CraftsmanRequestDetailsUiState(
    val request: RequestServiceUIState = RequestServiceUIState(),
    val offers: List<Offer> = emptyList(),
    val yourOffers: List<Offer> = emptyList(),
    val craftsmanRequestDetails: CraftsmanRequestDetails? = null,
    val acceptedOffer: Offer? = null,
    val customer: Customer = Customer(),
)

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