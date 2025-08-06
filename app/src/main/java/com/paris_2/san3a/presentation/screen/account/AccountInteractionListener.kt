package com.paris_2.san3a.presentation.screen.account

import android.net.Uri

interface AccountInteractionListener {
    fun onPreviousClicked()
    fun onUserTypeSelected(type: UserType)
    fun onToggleServiceClicked(serviceId: String)
    fun onCustomerNameChanged(name: String)
    fun onGovernmentSelected(government: String)
    fun onCitiesSelected(city: String)
    fun onDescriptionChanged(description: String)
    fun onGovernmentBottomSheetVisibilityToggled()
    fun onGovernmentBottomSheetDismissed()
    fun onCitiesBottomSheetDismissed()
    fun onAddressDetailsChanged(addressDetails: String)
    fun onUserTypeButtonClicked()
    fun onServiceButtonClicked()
    fun onProfileButtonClicked()
    fun onLocationButtonClicked()
    fun onShowWorkButtonClicked()
    fun onVerifyIdentityButtonClicked()
    fun onDeleteWorkImageClicked(uri: Uri)
}