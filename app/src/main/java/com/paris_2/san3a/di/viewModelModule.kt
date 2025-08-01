package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.screen.messages.MessagesViewModel
import com.paris_2.san3a.presentation.screen.messagesDetails.MessagesDetailsViewModel
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::MessagesViewModel)
    viewModelOf(::MessagesDetailsViewModel)
    viewModelOf(::OnBoardingViewModel)
}