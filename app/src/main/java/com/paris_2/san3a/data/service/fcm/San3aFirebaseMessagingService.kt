package com.paris_2.san3a.data.service.fcm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.paris_2.san3a.R
import com.paris_2.san3a.data.source.local.userPreferences.UserPreferencesLocalDataStore
import com.paris_2.san3a.data.source.remote.user.UserRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class San3aFirebaseMessagingService(): FirebaseMessagingService() {

//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        Log.d("FCM", "Token: $token")
//        CoroutineScope(Dispatchers.IO).launch {
//            val userId = userPreferencesLocalDataStore.getPhoneNumber()
//            if (!userId.isNullOrBlank()) {
//                userRemoteDataSource.saveDeviceToken(userId, token)
//            } else {
//                Log.w("FCM", "No userId found, token not saved")
//            }
//        }
//
//    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "San3a"
        val body = remoteMessage.notification?.body ?: "New message received"

        showNotification(title, body)
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "san3a_channel"
        val notificationId = System.currentTimeMillis().toInt()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "San3a Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}