package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetAvailableJobsUseCase
import com.paris_2.san3a.domain.usecase.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.GetRecentRelatedJobsUseCase
import com.paris_2.san3a.domain.usecase.GetStatsUseCase
import com.paris_2.san3a.domain.usecase.SendMessageUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import org.koin.core.module.dsl.factoryOf
import com.paris_2.san3a.domain.usecase.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.IsPhoneNumberSavedUseCase
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SetOnboardingCompletedUseCase
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
    factoryOf(::IsPhoneNumberSavedUseCase)
    factoryOf(::GetAllServicesUseCase)
    factoryOf(::SendOtpUseCase)
    factoryOf(::GetStatsUseCase)
    factoryOf(::GetRecentRelatedJobsUseCase)
    factoryOf(::GetAvailableJobsUseCase)
    factoryOf(::GetMostRequestedServicesUseCase)
}