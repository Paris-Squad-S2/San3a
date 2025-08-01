package com.paris_2.san3a.di

import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SendOtpUseCase(get()) }
}
