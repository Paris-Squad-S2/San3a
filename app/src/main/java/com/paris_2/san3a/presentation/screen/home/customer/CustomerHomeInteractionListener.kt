package com.paris_2.san3a.presentation.screen.home.customer

interface CustomerHomeInteractionListener {
    fun onNotificationClick()
    fun onSearch(query: String)
    fun onServiceClick(serviceId: String)
    fun onBecomeCraftsmanClick()
    fun onDismissBottomSheet()
    fun createRequest(service: RequestServiceUiState , serviceId: String)
    fun updateNumOfRequests(serviceId: String)
    fun initBottomSheet(serviceTitle: String, serviceId: String, iconRes: Int)
    fun updateBottomSheetStep(step: BottomSheetStep)
    fun nextBottomSheetStep()
    fun previousBottomSheetStep()
    fun setBottomSheetServiceSubTitle(title: String)
    fun setBottomSheetDescription(description: String)
    fun setBottomSheetSelectedSuggestion(suggestion: String?)
    fun addBottomSheetImages(newImages: List<String>)
    fun deleteBottomSheetImageAt(index: Int)
    fun setBottomSheetAddressDetails(address: String)
    fun setBottomSheetSelectedGovernment(government: String)
    fun setBottomSheetSelectedCity(city: String)
    fun showGovernmentSheet(show: Boolean)
    fun showCitySheet(show: Boolean)
    fun resetBottomSheetState()

}