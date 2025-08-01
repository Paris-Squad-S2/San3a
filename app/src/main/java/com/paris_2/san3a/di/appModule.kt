package com.paris_2.san3a.di

import org.koin.dsl.module

val appModule = module {
    includes(navigationModule, dataModule,repositoryModule,useCaseModule,viewModelModule)
}