package com.paris_2.san3a.presentation.screen.more.moreScreen

interface MoreInteractionListener {
    fun onClickEditProfileBottomSheet()
    fun onClickSwitchAccountToCraftsman()
    fun onClickSwitchAccountToCustomer()
    fun onClickVerification()
    fun onClickMyService()
    fun onClickLanguage()
    fun onClickLocation()
    fun onClickLogout()
    fun onChangeToDarkMode(isDarkMode: Boolean)
    fun onClickNotification()
    fun onClickBecomeACraftsman()
    fun onNameValueChange(name: String)
    fun onCloseEditProfileBottomSheet()
    fun onCloseSelectedLanguageBottomSheet()
    fun onLanguageSelected(language: String)
    fun onClickRetry()
    fun onDismissSnackBar()
    fun onClickLogoutArrow()
    fun onDismissLogoutBottomSheet()
}