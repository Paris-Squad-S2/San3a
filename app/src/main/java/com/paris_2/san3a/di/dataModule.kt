package com.paris_2.san3a.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.data.repository.LocationRepositoryImp
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl

import com.paris_2.san3a.data.source.AppPreferences
import org.koin.android.ext.koin.androidApplication
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.domain.repository.LocationRepository
import org.koin.dsl.module

val dataModule = module {
    single { AppPreferences(androidApplication().applicationContext) }
    single<FireStoreService> { FireStoreServiceImpl(get()) }
    single { FirebaseFirestore.getInstance() }
    single<LocationRepository> { LocationRepositoryImp(get()) }
    single { FirebaseStorage.getInstance()}
    single<MessagesRemoteDataSource> { MessagesRemoteDataSourceImp(get()) }
    single<StorageRemoteDataSource> { FirebaseStorageDataSource(get()) }
}