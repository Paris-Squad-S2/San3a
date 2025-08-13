package com.paris_2.san3a.presentation.screen.more.locationScreen

interface LocationInteractionListener {
    fun onAreaSelected(area: String)
    fun onStreetChanged(street: String)
    fun onClickSave()
    fun onClickRetry()
    fun onNavigateBack()
    fun onShowBottomSheet(type: LocationBottomSheetType)
    fun onDismissBottomSheet()
}