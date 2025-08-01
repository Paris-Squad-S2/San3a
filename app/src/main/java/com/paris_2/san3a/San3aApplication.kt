package com.paris_2.san3a

import android.app.Application
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.mp.KoinPlatform.getKoin

class San3aApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@San3aApplication)
            modules(appModule)

            val networkChecker: NetworkConnectionChecker = getKoin().get()
            networkChecker.startChecker()
        }
    }
}