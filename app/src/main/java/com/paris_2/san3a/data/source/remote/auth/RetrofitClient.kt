package com.paris_2.san3a.data.source.remote.auth

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.whatsapp.api.funtaste.xyz/"
    val WHATSAPP_API_KEY = "kikoe6wZMu7DwYl5t8uH5FqHIVMoWg4GKcIF"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", WHATSAPP_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()


    val api: AuthRemoteDataSource by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthRemoteDataSource::class.java)
    }
}
