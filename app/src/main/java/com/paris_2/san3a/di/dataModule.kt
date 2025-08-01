package com.paris_2.san3a.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.FirebaseFirestore
import com.paris_2.san3a.data.service.auth.AuthApiServices
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.data.repository.HomeRepositoryImpl
import com.paris_2.san3a.BuildConfig
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.remote.user.UserRemoteDataSourceImpl
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSourceImpl

import com.paris_2.san3a.data.source.AppPreferences
import org.koin.android.ext.koin.androidApplication
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSource
import com.paris_2.san3a.data.source.remote.service.ServiceRemoteDataSourceImpl
import okhttp3.OkHttpClient
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.domain.repository.HomeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import com.paris_2.san3a.data.source.remote.user.UserRemoteDataSource
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.whatsapp.api.funtaste.xyz/"
val dataModule = module {
    singleOf(::FireStoreServiceImpl) { bind<FireStoreService>() }
    singleOf(::MessagesRemoteDataSourceImp) { bind<MessagesRemoteDataSource>() }
    singleOf(::FirebaseStorageDataSource) { bind<StorageRemoteDataSource>() }
    singleOf(::UserRemoteDataSourceImpl) { bind<UserRemoteDataSource>() }
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
                    .addHeader("x-api-key", BuildConfig.WHATSAPP_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    single { AppPreferences(androidApplication().applicationContext) }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create { get<Context>().preferencesDataStoreFile("app_datastore") }
    }
}