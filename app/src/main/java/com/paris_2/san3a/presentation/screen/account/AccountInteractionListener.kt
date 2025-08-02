package com.paris_2.san3a.presentation.screen.account

interface AccountInteractionListener {
    fun onPreviousClicked()
    fun onNextClicked()
    fun onUserTypeSelected(type: UserType)
    fun onToggleServiceClicked(serviceId: String)
    fun onCustomerNameChanged(name: String)
    fun onDescriptionChanged(description: String)
}