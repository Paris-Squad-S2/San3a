package com.paris_2.san3a.di

import com.google.firebase.firestore.FirebaseFirestore
import com.paris_2.san3a.data.repository.LocationRepositoryImp
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.domain.repository.LocationRepository
import org.koin.dsl.module

val dataModule = module {
    single<FireStoreService> { FireStoreServiceImpl(get()) }
    single { FirebaseFirestore.getInstance() }
    single<LocationRepository> { LocationRepositoryImp(get()) }
    single<MessagesRemoteDataSource> { MessagesRemoteDataSourceImp(get()) }

}