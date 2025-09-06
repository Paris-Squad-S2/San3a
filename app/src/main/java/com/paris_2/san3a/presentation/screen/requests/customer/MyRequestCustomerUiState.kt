package com.paris_2.san3a.presentation.screen.requests.customer

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.presentation.utill.fakeImage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime


data class MyRequestCustomerScreenState(
    val isLoading: Boolean = true,
    val myRequestCustomerUiState: MyRequestCustomerUiState = MyRequestCustomerUiState(),
    val errorMessage: String? = null,
)

data class MyRequestCustomerUiState(
    val customerPhone: String = "",
    val notificationsCount: Int = 0,
    val isRatingVisible: Boolean = false,
    val ongoing: Map<String, MyRequestCustomerUi> = emptyMap(),
    val completed: Map<String, MyRequestCustomerUi> = emptyMap(),
    val canceled: Map<String, MyRequestCustomerUi> = emptyMap(),
    val services: Map<String, Service> = emptyMap(),
    val rating: Float = 0.0f,
    val craftsmanToRate: String = "",
    val offerToRate: String = "",
)

fun RequestService.toRequestServiceUiState(services: Map<String, Service>): MyRequestCustomerUi {
    return MyRequestCustomerUi(
        id = this.id,
        requestTitle = this.title,
        status = this.requestStatus,
        serviceId = this.serviceId,
        date = this.time.date,
        time = time.time,
        serviceImage = services[this.serviceId]?.imageUrl.orEmpty(),
    )
}

fun List<RequestService>.toRequestServiceUiStateMap(services: Map<String, Service>): Map<String, MyRequestCustomerUi> {
    return this.associateBy { it.id }.mapValues { it.value.toRequestServiceUiState(services) }
}

data class MyRequestCustomerUi(
    val id: String = "",
    val requestTitle: String = "",
    val offersCount: Int = 0,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val serviceId: String = "",
    val serviceType: String = "",
    val serviceImage : String = "",
    val status: RequestStatus = RequestStatus.ONGOING,
    val offer: OfferUiState = OfferUiState(),
)

fun User.toCraftsManUiState(rating: Float, craftsManRatingForOffer: Float?) : CraftsManUiState {
    return CraftsManUiState(
        profileUrl = this.profilePhoto ?: fakeImage,
        name = this.fullName,
        review = 150,
        rating = rating,
        craftsManRatingForOffer = craftsManRatingForOffer,
        phoneNumber = this.id,
        isVerify = this.nationalIdBackImage.isNotBlank() && this.nationalIdFrontImage.isNotBlank(),
    )
}

data class CraftsManUiState(
    val profileUrl: String? = null,
    val name: String = "",
    val review: Int = 0,
    val rating: Float = 0.0f,
    val craftsManRatingForOffer: Float? = null,
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
