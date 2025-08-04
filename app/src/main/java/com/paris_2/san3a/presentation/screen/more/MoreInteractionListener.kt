package com.paris_2.san3a.presentation.screen.more

interface MoreInteractionListener {
    fun onClickEditProfileBottomSheet()
    fun onClickSwitchAccountToCraftsman()
    fun onClickSwitchAccountToCustomer()
    fun onClickLanguage()
    fun onClickLocation()
    fun onClickLogout()
    fun onChangeToDarkMode(isDarkMode: Boolean)
    fun onClickNotification()
    fun onClickBecomeACraftsman()
    fun onNameValueChange(name: String)
    fun onCloseEditProfileBottomSheet()
    fun onPickImageClick()
}
