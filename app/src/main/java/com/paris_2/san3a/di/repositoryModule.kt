package com.paris_2.san3a.di

import com.paris_2.san3a.data.repository.AuthRepositoryImpl
import com.paris_2.san3a.data.repository.ChatRepositoryImpl
import com.paris_2.san3a.data.repository.LocationRepositoryImp
import com.paris_2.san3a.data.repository.MessageRepositoryImpl
import com.paris_2.san3a.data.repository.OnboardingRepositoryImpl
import com.paris_2.san3a.data.repository.ProfileRepositoryImpl
import com.paris_2.san3a.data.repository.UserRepositoryImp
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.repository.AuthRepository
import com.paris_2.san3a.domain.repository.ChatRepository
import com.paris_2.san3a.domain.repository.LocationRepository
import com.paris_2.san3a.domain.repository.MessageRepository
import com.paris_2.san3a.domain.repository.OnboardingRepository
import com.paris_2.san3a.domain.repository.ProfileRepository
import com.paris_2.san3a.domain.repository.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::MessageRepositoryImpl) { bind<MessageRepository>() }
    singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }
    singleOf(::UserRepositoryImp) { bind<UserRepository>() }
    singleOf(::OnboardingRepositoryImpl) { bind<OnboardingRepository>() }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    single { NetworkConnectionChecker(androidApplication().applicationContext) }
    singleOf(::LocationRepositoryImp) { bind<LocationRepository>() }
    singleOf(::ProfileRepositoryImpl) { bind<ProfileRepository>() }

}