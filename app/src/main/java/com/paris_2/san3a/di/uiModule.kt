package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.screen.register.OTPRegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::OTPRegisterViewModel)
}