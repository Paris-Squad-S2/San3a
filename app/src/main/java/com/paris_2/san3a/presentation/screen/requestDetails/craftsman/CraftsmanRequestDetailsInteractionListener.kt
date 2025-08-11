package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface CraftsmanRequestDetailsInteractionListener {

    fun onClickFavorite()
    fun onClickBack()
    fun onRetryClick()
    fun onSendOfferClick()
    fun onChatWithPosterClick(customerId: String)
    fun onCancelRequestClick(requestId: String)
    fun markAsDoneClick(requestId: String, price: Double)

    fun onPriceChanged(price: String)
    fun onDateChanged(date: LocalDate)
    fun onTimeChanged(time: LocalTime)
    fun onMessageChanged(message: String)
    fun onShowDatePickerChange(show: Boolean)
    fun onShowTimePickerChange(show: Boolean)
}