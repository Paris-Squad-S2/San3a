package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.SendMessageUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SendMessageUseCase(get()) }
    single { GetChatsByUserIdUseCase(get()) }
    single { GetMessagesByChatIdUseCase(get()) }
    single { DeleteChatByIdUseCase(get()) }
    single { SetUpAccountUseCase(get()) }
}