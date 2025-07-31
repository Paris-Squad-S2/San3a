package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.GetCurrentLocationUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetCurrentLocationUseCase(get()) }
}