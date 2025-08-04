package com.paris_2.san3a.presentation.screen.more

interface MoreInteractionListener {
    fun onClickEdit()
    fun onClickSwitchAccountToCraftsman()
    fun onClickSwitchAccountToCustomer()
    fun onClickLanguage()
    fun onClickLocation()
    fun onClickLogout()
    fun onChangeToDarkMode(isDarkMode: Boolean)
}
