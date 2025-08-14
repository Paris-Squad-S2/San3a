package com.paris_2.san3a.presentation.screen.requests.craftsman

import androidx.annotation.StringRes
import coil3.Uri
import coil3.toUri
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.User

data class MyJobsCraftsmanScreenState(
    val isLoading: Boolean = false,
    val myOffersCraftsmanUiState: MyOfferCraftsmanUiState = MyOfferCraftsmanUiState(),
    @StringRes val errorMessage: Int? = null,
    val isNoInternet: Boolean = false,
    val showSnackBarError: Boolean = false,
)

data class MyOfferCraftsmanUiState(
    val craftsManId: String = "",
    val ongoing: Map<String, JobUiState> = emptyMap(),
    val completed: Map<String, JobUiState> = emptyMap(),
    val canceled: Map<String, JobUiState> = emptyMap(),
)

data class JobUiState(
    val id: String = "",
    val customerPhone: String = "",
    val serviceType: String = "",
    val address: String = "",
    val status: RequestStatus = RequestStatus.ONGOING,
    val time: String = "",
    val title: String = "",
    val serviceId: String = "",
    val serviceImage: String = "",
    val description: String = "",
    val selectedCraftsmanId: String? = null,
    val offer: OfferUiState? = null,
)

fun RequestService.toMyJobOfferUiState(location: String): JobUiState {
    return JobUiState(
        id = this.id,
        customerPhone = this.userId,
        serviceType = this.serviceType,
        address = listOf(location, this.locationDetails)
            .filter { it.isNotBlank() }
            .joinToString(", "),
        status = this.requestStatus,
        time = this.time.toString(), //TODO
        serviceId = this.serviceId,
        serviceImage = "",
        title = this.title,
        selectedCraftsmanId = this.selectedCraftsmanId,
        description = this.description,
    )
}

data class OfferUiState(
    val id: String,
    val price: Double,
    val craftsmanId: String,
    val preferredDate: String,
    val preferredTime: String,
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
            preferredDate = it.preferredDate.toString(),
            preferredTime = it.preferredTime.toString(),
            messageToCustomer = it.messageToCustomer,
            isAccepted = it.isAccepted,
            craftsMan = CraftsManUiState()
        )
    }
}

fun User.toCraftsManUiState(rating: Float): CraftsManUiState {
    return CraftsManUiState(
        profileUrl = this.profilePhoto.toUri(),
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
