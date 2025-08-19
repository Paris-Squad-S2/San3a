package com.paris_2.san3a.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.data.repository.ServicesRepositoryImpl
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.local.appVersion.AppVersionDataSource
import com.paris_2.san3a.data.source.local.appVersion.AppVersionDataSourceImpl
import com.paris_2.san3a.data.source.local.userPreferences.UserPreferencesLocalDataStore
import com.paris_2.san3a.data.source.local.userPreferences.UserPreferencesLocalDataStoreImpl
import com.paris_2.san3a.data.source.local.location.LocationLocalDataSource
import com.paris_2.san3a.data.source.local.location.LocationLocalDataSourceImpl
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.notification.NotificationRemoteDataSource
import com.paris_2.san3a.data.source.remote.notification.NotificationRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.requests.RequestRemoteDataSource
import com.paris_2.san3a.data.source.remote.requests.RequestRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.data.source.remote.user.UserRemoteDataSource
import com.paris_2.san3a.data.source.remote.user.UserRemoteDataSourceImpl
import com.paris_2.san3a.domain.repository.ServicesRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create { get<Context>().preferencesDataStoreFile("app_datastore") }
    }
    singleOf(::FireStoreServiceImpl) { bind<FireStoreService>() }
    singleOf(::MessagesRemoteDataSourceImp) { bind<MessagesRemoteDataSource>() }
    singleOf(::FirebaseStorageDataSource) { bind<StorageRemoteDataSource>() }
    singleOf(::UserRemoteDataSourceImpl) { bind<UserRemoteDataSource>() }
    singleOf(::ServiceRemoteDataSourceImpl) { bind<ServiceRemoteDataSource>() }
    singleOf(::ServicesRepositoryImpl) { bind<ServicesRepository>() }
    singleOf(::AuthRemoteDataSourceImpl) { bind<AuthRemoteDataSource>() }
    singleOf(::LocationLocalDataSourceImpl) { bind<LocationLocalDataSource>() }
    singleOf(::AppVersionDataSourceImpl) { bind<AppVersionDataSource>() }
    singleOf(::UserPreferencesLocalDataStoreImpl) { bind<UserPreferencesLocalDataStore>() }
    singleOf(::NotificationRemoteDataSourceImpl) { bind<NotificationRemoteDataSource>() }
    singleOf(::RequestRemoteDataSourceImpl) { bind<RequestRemoteDataSource>() }
}