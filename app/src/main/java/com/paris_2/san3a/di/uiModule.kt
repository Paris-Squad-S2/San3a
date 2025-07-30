package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.screen.register.OTPRegisterViewModel
import com.paris_2.san3a.presentation.screen.register.registerScreen.RegisterViewModel
import com.paris_2.san3a.presentation.util.ActivityProvider
import com.paris_2.san3a.presentation.util.ActivityProviderImpl
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    single<ActivityProvider> { ActivityProviderImpl() }
    viewModelOf(::OTPRegisterViewModel)
    viewModelOf(::RegisterViewModel)
}