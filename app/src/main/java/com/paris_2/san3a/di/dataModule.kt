package com.paris_2.san3a.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import org.koin.dsl.module

val dataModule = module {
    single<FireStoreService> { FireStoreServiceImpl(get()) }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single<MessagesRemoteDataSource> { MessagesRemoteDataSourceImp(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }

}