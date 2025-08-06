package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import com.paris_2.san3a.domain.entity.Offer
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class CraftsmanRequestUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val craftsmanRequestDetails: CraftsmanRequestDetails? = null,
    val offers: List<Offer> = emptyList(),
    val yourOffer: List<Offer> = emptyList(),
    val acceptedOffers: List<Offer> = emptyList(),
    val customer: Customer? = null,
)

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