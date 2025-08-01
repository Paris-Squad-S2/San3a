package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.SendMessageUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import org.koin.core.module.dsl.factoryOf
import com.paris_2.san3a.domain.usecase.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.SetOnboardingCompletedUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::SendMessageUseCase)
    factoryOf(::GetChatsByUserIdUseCase)
    factoryOf(::GetMessagesByChatIdUseCase)
    factoryOf(::DeleteChatByIdUseCase)
    factoryOf(::SetUpAccountUseCase)
    factoryOf(::IsOnboardingCompletedUseCase)
    factoryOf(::SetOnboardingCompletedUseCase)
}