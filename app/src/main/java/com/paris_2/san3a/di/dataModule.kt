package com.paris_2.san3a.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import org.koin.dsl.module

val dataModule = module {
    single<FireStoreService> { FireStoreServiceImpl(get()) }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance()}
    single<MessagesRemoteDataSource> { MessagesRemoteDataSourceImp(get()) }
    single<StorageRemoteDataSource> { FirebaseStorageDataSource(get()) }
}