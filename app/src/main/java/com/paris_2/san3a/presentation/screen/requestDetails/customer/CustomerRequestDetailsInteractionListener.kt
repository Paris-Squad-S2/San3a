package com.paris_2.san3a.presentation.screen.requestDetails.customer

interface CustomerRequestDetailsInteractionListener {
    fun onClickBack()
    fun onClickActonDots()
    fun onRetryClick()
    fun onChartWithCraftsmanClick(craftsmanId: String)
    fun onAcceptOfferClick(offerId: String, craftsmanId: String)
}