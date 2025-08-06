package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.screen.account.AccountViewModel
import com.paris_2.san3a.presentation.screen.home.craftsman.CraftsmanHomeViewModel
import com.paris_2.san3a.presentation.screen.home.customer.CustomerHomeViewModel
import com.paris_2.san3a.presentation.screen.main.MainViewModel
import com.paris_2.san3a.presentation.screen.messages.MessagesViewModel
import com.paris_2.san3a.presentation.screen.messagesDetails.MessagesDetailsViewModel
import com.paris_2.san3a.presentation.screen.more.locationScreen.LocationViewModel
import com.paris_2.san3a.presentation.screen.more.moreScreen.MoreViewModel
import com.paris_2.san3a.presentation.screen.myService.MyServiceViewModel
import com.paris_2.san3a.presentation.screen.notification.NotificationViewModel
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingViewModel
import com.paris_2.san3a.presentation.screen.register.otpScreen.OTPRegisterViewModel
import com.paris_2.san3a.presentation.screen.register.registerScreen.RegisterViewModel
import com.paris_2.san3a.presentation.screen.verification.VerificationViewModel
import org.koin.core.module.dsl.viewModelOf
import com.paris_2.san3a.presentation.screen.notification.NotificationViewModel
import com.paris_2.san3a.presentation.screen.myRequest.customer.MyRequestCustomerViewModel
import com.paris_2.san3a.presentation.screen.myRequest.craftsman.MyRequestCraftsmanViewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::MessagesViewModel)
    viewModelOf(::MessagesDetailsViewModel)
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::CraftsmanHomeViewModel)
    viewModelOf(::CustomerHomeViewModel)
    viewModelOf(::OTPRegisterViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::NotificationViewModel)
    viewModelOf(::MoreViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::VerificationViewModel)
    viewModelOf(::MyServiceViewModel)
    viewModelOf(::LocationViewModel)
    viewModelOf(::MyRequestCustomerViewModel)
    viewModelOf(::MyRequestCraftsmanViewModel)
}