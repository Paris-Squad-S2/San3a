package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.screen.messagesDetails.MessagesDetailsViewModel

import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf


val viewModelModule = module {
    single { MessagesDetailsViewModel(get(), get(), get()) }
    viewModelOf(::OnBoardingViewModel)
}