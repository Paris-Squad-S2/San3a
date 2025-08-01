package com.paris_2.san3a.di

import com.paris_2.san3a.presentation.screen.message.details.MessagesDetailsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MessagesDetailsViewModel(get(), get(), get()) }
}