package com.paris_2.san3a.di

import com.paris_2.san3a.data.source.remote.user.service.AuthApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val OTP_BASE_URL = "https://www.whatsapp.api.funtaste.xyz/"
val networkModule = module {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    single<AuthApiServices> { get<Retrofit>().create(AuthApiServices::class.java) }
    single {
        Retrofit.Builder()
            .baseUrl(OTP_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2ODhlNWQ3ZTFlNjZkZWQ5NmU0MTdjNTgiLCJpYXQiOjE3NTQzMDc2MTF9.0AVjQpt4ahSlwUIfyXA0JuYhm9ppJrH1FZVXu2PdLns")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}