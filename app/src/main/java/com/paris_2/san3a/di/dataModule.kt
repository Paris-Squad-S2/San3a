package com.paris_2.san3a.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.paris_2.san3a.data.service.auth.AuthApiServices
import com.google.firebase.storage.FirebaseStorage
import com.paris_2.san3a.BuildConfig
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSource
import com.paris_2.san3a.data.source.remote.auth.AuthRemoteDataSourceImpl

import com.paris_2.san3a.data.source.AppPreferences
import org.koin.android.ext.koin.androidApplication
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSourceImp
import okhttp3.OkHttpClient
import com.paris_2.san3a.data.source.remote.storage.FirebaseStorageDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.whatsapp.api.funtaste.xyz/"
val dataModule = module {
    single { AppPreferences(androidApplication().applicationContext) }
    single<FireStoreService> { FireStoreServiceImpl(get()) }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance()}
    single<MessagesRemoteDataSource> { MessagesRemoteDataSourceImp(get()) }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get()) }
    single<AuthApiServices> {
        get<Retrofit>().create(AuthApiServices::class.java)
    }
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
    single<StorageRemoteDataSource> { FirebaseStorageDataSource(get()) }
}