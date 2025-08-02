package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.screen.account.AccountViewModel
import com.paris_2.san3a.presentation.screen.message.details.MessagesDetailsViewModel
import com.paris_2.san3a.presentation.screen.messages.MessagesViewModel
import org.koin.core.module.dsl.viewModelOf
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingViewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::MessagesViewModel)
    viewModelOf(::MessagesDetailsViewModel)
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::AccountViewModel)
}