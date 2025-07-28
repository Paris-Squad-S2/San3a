package com.paris_2.san3a.di

import com.google.firebase.firestore.FirebaseFirestore
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceImpl
import org.koin.dsl.module

val dataModule = module {
    single<FireStoreService> { FireStoreServiceImpl(get()) }
    single { FirebaseFirestore.getInstance() }
}