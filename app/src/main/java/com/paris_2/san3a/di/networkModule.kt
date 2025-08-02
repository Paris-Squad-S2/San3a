package com.paris_2.san3a.di

import com.paris_2.san3a.BuildConfig
import com.paris_2.san3a.data.service.auth.AuthApiServices
import com.paris_2.san3a.data.service.location.LocationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.whatsapp.api.funtaste.xyz/"
private const val LOCATION_BASE_URL = "https://countriesnow.space/api/v0.1/countries/"
val networkModule = module {
    single<AuthApiServices> { get<Retrofit>(named("authRetrofit")).create(AuthApiServices::class.java) }
    single(named("authRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(named("authOkHttp")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("authOkHttp")) {
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

    single<LocationService> { get<Retrofit>(named("locationRetrofit")).create(LocationService::class.java) }
    single(named("locationRetrofit")) {
        Retrofit.Builder()
            .baseUrl(LOCATION_BASE_URL)
            .client(get(named("locationOkHttp")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    single(named("locationOkHttp")) {
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}