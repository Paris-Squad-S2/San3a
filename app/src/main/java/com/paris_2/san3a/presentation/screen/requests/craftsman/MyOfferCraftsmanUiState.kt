package com.paris_2.san3a.presentation.screen.requests.craftsman

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus

data class MyJobsCraftsmanScreenState(
    val isLoading: Boolean = false,
    val myOffersCraftsmanUiState: MyOfferCraftsmanUiState = MyOfferCraftsmanUiState(),
    val errorMessage: String? = null
)

data class MyOfferCraftsmanUiState(
    val craftsManId: String = "",

    val requests: Map<String, JobUiState> = emptyMap(),

    val ongoing: List<JobUiState> = emptyList(),
    val completed: List<JobUiState> = emptyList(),
    val canceled: List<JobUiState> = emptyList(),
)

fun List<RequestService>.toMyJobOfferUiStateMap(): Map<String, JobUiState> {
    return this.associateBy { it.id }.mapValues { it.value.toMyJobOfferUiState() }
}

data class JobUiState(
    val id: String,
    val customerPhone: String,
    val serviceType: String,
    val address: String,
    val status: RequestStatus,
    val offer: OfferUiState? = null,
)

fun RequestService.toMyJobOfferUiState(): JobUiState {
    return JobUiState(
        id = this.id,
        customerPhone = this.userId,
        serviceType = this.serviceType,
        address = this.location,
        status = this.requestStatus
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

fun Offer?.toUiState(): OfferUiState? {
    return this?.let {
        OfferUiState(
            id = it.id,
            price = it.price,
            preferredDate = it.preferredDate.toString(),
            preferredTime = it.preferredTime.toString(),
            messageToCustomer = it.messageToCustomer,
            isAccepted = it.isAccepted,
        )
    }
}

fun List<Offer>.toUiStateList(): List<OfferUiState?> = map { it.toUiState() }

fun List<RequestService>.toMyJobOfferUiStateList(): List<JobUiState> {
    return this.map { it.toMyJobOfferUiState() }
}

