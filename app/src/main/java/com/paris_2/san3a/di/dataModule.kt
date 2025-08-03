package com.paris_2.san3a.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.data.repository.HomeRepositoryImpl
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.data.source.local.LocalDataStoreImpl
import com.paris_2.san3a.data.source.remote.UserRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.location.LocationRemoteDataSource
import com.paris_2.san3a.data.source.remote.location.LocationRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.notification.NotificationDataSource
import com.paris_2.san3a.data.source.remote.notification.NotificationDataSourceImpl
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.domain.repository.HomeRepository
import com.paris_2.san3a.domain.repository.UserRemoteDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::FireStoreServiceImpl) { bind<FireStoreService>() }
    singleOf(::MessagesRemoteDataSourceImp) { bind<MessagesRemoteDataSource>() }
    singleOf(::FirebaseStorageDataSource) { bind<StorageRemoteDataSource>() }
    singleOf(::UserRemoteDataSourceImp) { bind<UserRemoteDataSource>() }
    singleOf(::ServiceRemoteDataSourceImpl) { bind<ServiceRemoteDataSource>() }
    singleOf(::HomeRepositoryImpl) { bind<HomeRepository>() }
    singleOf(::MessagesRemoteDataSourceImp) { bind<MessagesRemoteDataSource>() }
    singleOf(::FirebaseStorageDataSource) { bind<StorageRemoteDataSource>() }
    singleOf(::AuthRemoteDataSourceImpl) { bind<AuthRemoteDataSource>() }
    singleOf(::LocationRemoteDataSourceImp) { bind<LocationRemoteDataSource>() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    singleOf(::LocalDataStoreImpl) { bind<LocalDataStore>() }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create { get<Context>().preferencesDataStoreFile("app_datastore") }
    }
    singleOf(::NotificationDataSourceImpl) { bind<NotificationDataSource>() }
}