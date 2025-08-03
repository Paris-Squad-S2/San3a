package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.IsLoggedInUseCase
import com.paris_2.san3a.domain.usecase.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.SendMessageUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import org.koin.core.module.dsl.factoryOf
import com.paris_2.san3a.domain.usecase.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SetLoginUseCase
import com.paris_2.san3a.domain.usecase.SetOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.GetWorkMediaUseCase
import com.paris_2.san3a.domain.usecase.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.StreamNotificationsUseCase

import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::SendMessageUseCase)
    factoryOf(::GetChatsByUserIdUseCase)
    factoryOf(::GetMessagesByChatIdUseCase)
    factoryOf(::DeleteChatByIdUseCase)
    factoryOf(::SetUpAccountUseCase)
    factoryOf(::IsOnboardingCompletedUseCase)
    factoryOf(::SetOnboardingCompletedUseCase)
    factoryOf(::SavePhoneNumberUseCase)
    factoryOf(::GetAllServicesUseCase)
    factoryOf(::SendOtpUseCase)
    factoryOf(::IsLoggedInUseCase)
    factoryOf(::SetLoginUseCase)
    factoryOf(::GetPhoneNumberUseCase)
    factoryOf(::GetLocationInfoUseCase)
    factoryOf(::GetUserServicesUseCase)
    factoryOf(::GetUserUseCase)
    factoryOf(::GetWorkMediaUseCase)
    factoryOf(::AddNotificationUseCase)
    factoryOf(::StreamNotificationsUseCase)

}