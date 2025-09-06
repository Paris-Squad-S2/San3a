package com.paris_2.san3a.presentation.screen.requests.craftsman

import coil3.Uri
import coil3.toUri
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.presentation.shared.utils.UiText
import com.paris_2.san3a.presentation.utill.fakeImage
import kotlinx.datetime.LocalDateTime

data class MyJobsCraftsmanScreenState(
    val isLoading: Boolean = true,
    val myOffersCraftsmanUiState: MyOfferCraftsmanUiState = MyOfferCraftsmanUiState(),
    val errorMessage: UiText? = null,
    val isNoInternet: Boolean = false,
    val showSnackBarError: Boolean = false,
)

data class MyOfferCraftsmanUiState(
    val craftsManId: String = "",
    val notificationsCount: Int = 0,
    val ongoing: Map<String, JobUiState> = emptyMap(),
    val completed: Map<String, JobUiState> = emptyMap(),
    val canceled: Map<String, JobUiState> = emptyMap(),
    val userServices: Map<String, Service> = emptyMap(),
)

data class JobUiState(
    val id: String = "",
    val customerPhone: String = "",
    val address: String = "",
    val status: RequestStatus = RequestStatus.ONGOING,
    val time: String = "",
    val title: String = "",
    val serviceId: String = "",
    val serviceType: String = "",
    val serviceImage: String = "",
    val description: String = "",
    val selectedCraftsmanId: String? = null,
    val offer: OfferUiState? = null,
)

fun RequestService.toMyJobOfferUiState(location: String, serviceImage: String?): JobUiState {
    return JobUiState(
        id = this.id,
        customerPhone = this.userId,
        address = listOf(location, this.locationDetails)
            .filter { it.isNotBlank() }
            .joinToString(", "),
        status = this.requestStatus,
        time = this.time.toString(), //TODO
        serviceId = this.serviceId,
        serviceImage = serviceImage ?: "",
        title = this.title,
        selectedCraftsmanId = this.selectedCraftsmanId,
        description = this.description,
    )
}

data class OfferUiState(
    val id: String,
    val price: Double,
    val craftsmanId: String,
    val preferredDateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val messageToCustomer: String,
    val isAccepted: Boolean,
    val craftsMan: CraftsManUiState
)

fun Offer?.toUiState(): OfferUiState? {
    return this?.let {
        OfferUiState(
            id = it.id,
            price = it.price,
            craftsmanId = it.craftsmanId,
            preferredDateTime = LocalDateTime(it.preferredDate, it.preferredTime),
            createdAt = it.createdAt,
            messageToCustomer = it.messageToCustomer,
            isAccepted = it.isAccepted,
            craftsMan = CraftsManUiState()
        )
    }
}

fun User.toCraftsManUiState(rating: Float): CraftsManUiState {
    return CraftsManUiState(
        profileUrl = this.profilePhoto?.toUri() ?: fakeImage.toUri(),
        name = this.fullName,
        review = 150,
        rating = rating,
        phoneNumber = this.id,
        isVerify = this.nationalIdBackImage.isNotBlank() && this.nationalIdFrontImage.isNotBlank(),
    )
}

data class CraftsManUiState(
    val profileUrl: Uri? = null,
    val name: String = "",
    val review: Int = 0,
    val rating: Float = 0.0f,
    val phoneNumber: String = "",
    val isVerify: Boolean = false,
)

enum class ListType {
    ONGOING, COMPLETED, CANCELED
}
