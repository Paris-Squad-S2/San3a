package com.paris_2.san3a.presentation.screen.account

import android.net.Uri
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate

interface AccountInteractionListener {
    fun onPreviousClicked()
    fun onUserTypeSelected(type: UserType)
    fun onToggleServiceClicked(serviceId: String)
    fun onCustomerNameChanged(name: String)
    fun onGovernmentSelected(governorate: Governorate)
    fun onCitiesSelected(city: City)
    fun onDescriptionChanged(description: String)
    fun onGovernmentBottomSheetVisibilityToggled()
    fun onGovernmentBottomSheetDismissed()
    fun onAddressDetailsChanged(addressDetails: String)
    fun onUserTypeButtonClicked()
    fun onServiceButtonClicked()
    fun onProfileButtonClicked()
    fun onLocationButtonClicked()
    fun onShowWorkButtonClicked()
    fun onClickVerifyLater()
    fun onVerifyIdentityButtonClicked()
    fun onDeleteWorkImageClicked(uri: Uri)
}