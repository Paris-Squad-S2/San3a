package com.paris_2.san3a.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.local.UserLocalDataSourceImp
import com.paris_2.san3a.data.source.remote.UserRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.domain.repository.UserRemoteDataSource
import com.paris_2.san3a.domain.source.local.UserLocalDataSource
import org.koin.dsl.module

val dataModule = module {
    single<FireStoreService> { FireStoreServiceImpl(get()) }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance()}
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create { get<Context>().preferencesDataStoreFile("app_datastore") }
    }
    single<MessagesRemoteDataSource> { MessagesRemoteDataSourceImp(get()) }
    single<StorageRemoteDataSource> { FirebaseStorageDataSource(get()) }
    single<UserRemoteDataSource> { UserRemoteDataSourceImp(get()) }
    single<UserLocalDataSource> { UserLocalDataSourceImp(get()) }
}