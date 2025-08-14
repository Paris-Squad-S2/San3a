package com.paris_2.san3a.presentation.screen.requests.customer

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.User


data class MyRequestCustomerScreenState(
    val isLoading: Boolean = false,
    val myRequestCustomerUiState: MyRequestCustomerUiState = MyRequestCustomerUiState(),
    val errorMessage: String? = null,
)

data class MyRequestCustomerUiState(
    val customerPhone: String = "",
    val isRatingVisible: Boolean = false,
    val ongoing: Map<String, MyRequestCustomerUi> = emptyMap(),
    val completed: Map<String, MyRequestCustomerUi> = emptyMap(),
    val canceled: Map<String, MyRequestCustomerUi> = emptyMap(),
    val services: Map<String, String> = emptyMap(),
    val rating: Float = 0.0f,
    val craftsmanToRate: String = ""
)

fun RequestService.toRequestServiceUiState(services: Map<String, String>): MyRequestCustomerUi {
    return MyRequestCustomerUi(
        id = this.id,
        requestTitle = this.title,
        status = this.requestStatus,
        serviceType = this.serviceType,
        serviceId = this.serviceId,
        date = this.time.date.toString(),
        time = this.time.time.toString(),
        serviceImage = services[this.serviceId] ?: ""
    )
}

fun List<RequestService>.toRequestServiceUiStateMap(services: Map<String, String>): Map<String, MyRequestCustomerUi> {
    return this.associateBy { it.id }.mapValues { it.value.toRequestServiceUiState(services) }
}

data class MyRequestCustomerUi(
    val id: String = "",
    val requestTitle: String = "",
    val serviceType: String = "",
    val offersCount: Int = 0,
    val date: String = "",
    val time: String = "",
    val serviceId: String = "",
    val serviceImage : String = "",
    val status: RequestStatus = RequestStatus.ONGOING,
    val offer: OfferUiState = OfferUiState(),
    val craftsMan: CraftsManUiState = CraftsManUiState(),
)

fun User.toCraftsManUiState(rating: Float) : CraftsManUiState {
    return CraftsManUiState(
        profileUrl = this.profilePhoto,
        name = this.fullName,
        review = 150,
        rating = rating,
        phoneNumber = this.id,
        isVerify = this.nationalIdBackImage.isNotBlank() && this.nationalIdFrontImage.isNotBlank(),
    )
}

data class CraftsManUiState(
    val profileUrl: String? = null,
    val name: String = "",
    val review: Int = 0,
    val rating: Float = 0.0f,
    val phoneNumber: String = "",
    val isVerify: Boolean = false,
)

data class OfferUiState(
    val id: String = "",
    val price: Double = 0.0,
    val craftsmanId: String = "",
    val preferredDate: String = "",
    val preferredTime: String = "",
    val messageToCustomer: String = "",
    val isAccepted: Boolean = false,
    val craftsMan: CraftsManUiState = CraftsManUiState()
)

fun Offer.toUiState(): OfferUiState {
    return this.let {
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
