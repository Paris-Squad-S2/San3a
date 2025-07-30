package com.paris_2.san3a.di

import com.paris_2.san3a.data.repository.AuthRepositoryImpl
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(),get()) }
    single { NetworkConnectionChecker(get()) }
}