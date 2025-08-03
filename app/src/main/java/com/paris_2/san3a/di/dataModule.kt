package com.paris_2.san3a.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.BuildConfig
import com.paris_2.san3a.data.repository.HomeRepositoryImpl
import com.paris_2.san3a.data.service.auth.AuthApiServices
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.local.LocalDataStore
import com.paris_2.san3a.data.source.local.LocalDataStoreImpl
import com.paris_2.san3a.data.source.remote.UserRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.domain.repository.HomeRepository
import com.paris_2.san3a.domain.repository.UserRemoteDataSource
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.whatsapp.api.funtaste.xyz/"
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

    single<AuthApiServices> { get<Retrofit>().create(AuthApiServices::class.java) }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.WHATSAPP_API_KEY}")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("AppPrefStorage")
        }
    }

    singleOf(::LocalDataStoreImpl) { bind<LocalDataStore>() }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create { get<Context>().preferencesDataStoreFile("app_datastore") }
    }
}