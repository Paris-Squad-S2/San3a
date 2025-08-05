package com.paris_2.san3a.presentation.screen.myService

interface MyServiceInteractionListener{
    fun onBackClick()
    fun onClickSave()
    fun onClickRetry()
    fun onClickService(serviceId: String)
}