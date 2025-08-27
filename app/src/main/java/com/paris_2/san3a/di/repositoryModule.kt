package com.paris_2.san3a.di

import com.paris_2.san3a.data.repository.LocationRepositoryImp
import com.paris_2.san3a.data.repository.MessagingRepositoryImpl
import com.paris_2.san3a.data.repository.NotificationRepositoryImpl
import com.paris_2.san3a.data.repository.UserPreferencesRepositoryImpl
import com.paris_2.san3a.data.repository.RequestsRepositoryImpl
import com.paris_2.san3a.data.repository.UserRepositoryImpl
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.repository.LocationRepository
import com.paris_2.san3a.domain.repository.MessagingRepository
import com.paris_2.san3a.domain.repository.NotificationRepository
import com.paris_2.san3a.domain.repository.UserPreferencesRepository
import com.paris_2.san3a.domain.repository.RequestsRepository
import com.paris_2.san3a.domain.repository.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    single { NetworkConnectionChecker(androidApplication().applicationContext) }
    singleOf(::MessagingRepositoryImpl) { bind<MessagingRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::LocationRepositoryImp) { bind<LocationRepository>() }
    singleOf(::NotificationRepositoryImpl) { bind<NotificationRepository>() }
    singleOf(::UserPreferencesRepositoryImpl) { bind<UserPreferencesRepository>() }
    singleOf(::RequestsRepositoryImpl) { bind<RequestsRepository>() }
}