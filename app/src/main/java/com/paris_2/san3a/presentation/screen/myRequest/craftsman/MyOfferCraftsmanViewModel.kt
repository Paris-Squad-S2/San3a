package com.paris_2.san3a.presentation.screen.myRequest.craftsman

import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.requests.GetCustomerRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.myRequest.customer.MyRequestCustomerInteractionListener
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MyOfferCraftsmanViewModel(

) : BaseViewModel<MyOfferCraftsmanScreenState>(MyOfferCraftsmanScreenState()), MyRequestCustomerInteractionListener {

    override fun onRequestClick(requestId: String) {
        TODO("Not yet implemented")
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onRetryClick() {
    }
}