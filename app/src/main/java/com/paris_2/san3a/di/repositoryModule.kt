package com.paris_2.san3a.di

import com.paris_2.san3a.data.repository.AuthRepositoryImpl
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.repository.AuthRepository
import com.paris_2.san3a.data.repository.ChatRepositoryImpl
import com.paris_2.san3a.data.repository.MessageRepositoryImpl
import com.paris_2.san3a.data.repository.UserRepositoryImpl
import com.paris_2.san3a.data.repository.OnboardingRepositoryImpl
import com.paris_2.san3a.domain.repository.ChatRepository
import com.paris_2.san3a.domain.repository.MessageRepository
import com.paris_2.san3a.domain.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import com.paris_2.san3a.domain.repository.OnboardingRepository
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::MessageRepositoryImpl) { bind<MessageRepository>() }
    singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::OnboardingRepositoryImpl) { bind<OnboardingRepository>() }
    single<AuthRepository> { AuthRepositoryImpl(get(),get(),get()) }
    single { NetworkConnectionChecker(get()) }
}