package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.Service

interface CustomerHomeInteractionListener {
    fun onNotificationClick()
    fun onSearch(query: String)
    fun onServiceClick(service: Service)
    fun onBecomeCraftsmanClick()
    fun onDismissBottomSheet()
    fun createRequest()
    fun updateNumOfRequests(serviceId: String)
    fun initBottomSheet(service: Service)
    fun updateBottomSheetStep(step: BottomSheetStep)
    fun nextBottomSheetStep()
    fun previousBottomSheetStep()
    fun setBottomSheetServiceSubTitle(title: String)
    fun setBottomSheetDescription(description: String)
    fun setBottomSheetSelectedSuggestion(suggestion: String?)
    fun addBottomSheetImages(newImages: List<String>)
    fun deleteBottomSheetImageAt(index: Int)
    fun setBottomSheetAddressDetails(address: String)
    fun setBottomSheetSelectedGovernment(governmentId: Int)
    fun setBottomSheetSelectedCity(cityId: Int)
    fun showGovernmentSheet(show: Boolean)
    fun resetBottomSheetState()
    fun onMicClick()
    fun onSpeechRecognized(query: String)
    fun onDismissSnackBar()
    fun onRetry()
}