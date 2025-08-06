package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

interface CraftsmanInteractionListener {

    fun onClickFavorite()

    fun onClickAddOffer(offer: OfferUiState)
    fun onClickSendMessage(customerId: String)
}