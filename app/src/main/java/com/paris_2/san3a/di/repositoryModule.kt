package com.paris_2.san3a.di

import com.paris_2.san3a.data.repository.ChatRepositoryImpl
import com.paris_2.san3a.data.repository.MessageRepositoryImpl
import com.paris_2.san3a.domain.repository.ChatRepository
import com.paris_2.san3a.domain.repository.MessageRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MessageRepository> { MessageRepositoryImpl(get(), get()) }
    single<ChatRepository> { ChatRepositoryImpl(get()) }
}