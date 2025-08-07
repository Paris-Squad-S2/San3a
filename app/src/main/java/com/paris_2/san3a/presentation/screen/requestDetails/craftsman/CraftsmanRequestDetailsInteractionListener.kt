package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface CraftsmanRequestDetailsInteractionListener {

    fun onClickFavorite()
    fun onClickBack()
    fun onRetryClick()
    fun onClickSendMessage(customerId: String)
    fun onSendOfferClick()
    fun onChatWithPosterClick(customerId: String)
    fun onAcceptOfferClick(offerId: String)

    fun onPriceChanged(price: String)
    fun onDateChanged(date: LocalDate)
    fun onTimeChanged(time: LocalTime)
    fun onMessageChanged(message: String)
}