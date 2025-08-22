package com.paris_2.san3a.presentation.screen.more.locationScreen

interface LocationInteractionListener {
    fun onGovernorateSelected(governorateId: Int)
    fun onCityChanged(cityId: Int)
    fun onAddressInDetailsChange(address: String)
    fun onClickSave()
    fun onClickRetry()
    fun onNavigateBack()
    fun onShowBottomSheet(type: LocationBottomSheetType)
    fun onDismissBottomSheet()
}