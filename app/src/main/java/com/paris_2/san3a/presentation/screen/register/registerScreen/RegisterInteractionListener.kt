package com.paris_2.san3a.presentation.screen.register.registerScreen

import com.paris_2.san3a.presentation.screen.register.components.BottomSheetType

interface RegisterInteractionListener {
    fun onPhoneNumberChanged(phone: String)
    fun onClickContinue()
    fun changeBottomSheetType(bottomSheetType: BottomSheetType?)

}