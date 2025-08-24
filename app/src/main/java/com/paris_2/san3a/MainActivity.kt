package com.paris_2.san3a

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.paris_2.san3a.domain.usecase.SendNotificationUseCase
import com.paris_2.san3a.presentation.San3aScaffold
import com.paris_2.san3a.presentation.utill.InstallSavedAppLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainActivity : AppCompatActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val sendNotificationUseCase = SendNotificationUseCase()

        CoroutineScope(Dispatchers.IO).launch {
            val success = sendNotificationUseCase(
                title = "Hello",
                token = "cGnm4QghRsC3AcBpEYQiaJ:APA91bGC4RTMoEVPxIJB2atmialJashu5fhUdYdByDMGoB5veB2l-1jKZ6jdVRk0H7zfjD9_A4rOBktM6fNoWk6KliVlKnJ8vpd-Tuxo6W_xI81hcTccuns",
                description = "This is a test notification"
            )

            if (success) {
                Log.d("FCM", "success")
                println("Notification sent!")
            } else {
                Log.d("FCM", "failed")
                println("Failed to send notification")
            }
        }

        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            San3aScaffold()
        }
    }
}