package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

interface CraftsmanRequestDetailsInteractionListener {

    fun onClickFavorite()
    fun onClickBack()
    fun onRetryClick()
    fun onClickAddOffer(offer: OfferUiState)
    fun onClickSendMessage(customerId: String)
    fun onSendOfferClick()
}