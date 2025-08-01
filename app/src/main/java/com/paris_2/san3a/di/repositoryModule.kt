package com.paris_2.san3a.di

import com.paris_2.san3a.data.repository.AuthRepositoryImpl
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.repository.AuthRepository
import com.paris_2.san3a.data.repository.ChatRepositoryImpl
import com.paris_2.san3a.data.repository.MessageRepositoryImpl
import com.paris_2.san3a.data.repository.OnboardingRepositoryImpl
import com.paris_2.san3a.domain.repository.ChatRepository
import com.paris_2.san3a.domain.repository.MessageRepository
import com.paris_2.san3a.domain.repository.OnboardingRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(),get()) }
    single { NetworkConnectionChecker(get()) }
    single<MessageRepository> { MessageRepositoryImpl(get(), get()) }
    single<ChatRepository> { ChatRepositoryImpl(get()) }
    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }
}