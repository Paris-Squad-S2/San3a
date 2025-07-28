package com.paris_2.san3a.di
import com.paris_2.san3a.domain.usecase.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.SetOnboardingCompletedUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::IsOnboardingCompletedUseCase)
    factoryOf(::SetOnboardingCompletedUseCase)
}