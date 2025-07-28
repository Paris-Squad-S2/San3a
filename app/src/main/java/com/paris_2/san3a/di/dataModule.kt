package com.paris_2.san3a.di


import com.paris_2.san3a.presentation.screen.onboarding.AppPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single { AppPreferences(androidApplication().applicationContext) }
}