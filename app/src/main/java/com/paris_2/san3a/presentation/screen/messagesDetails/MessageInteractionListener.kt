package com.paris_2.san3a.presentation.screen.messagesDetails

interface MessageInteractionListener {
    fun onBackClick()
    fun onDropMenuClick()
    fun onDismissDropMenu()
    fun onDropMenuItemClick()
    fun onDeleteButtonClick()
    fun onDismissDeleteBottomSheet()
    fun onRetryClick()
    fun onDismissSnackBar()
}