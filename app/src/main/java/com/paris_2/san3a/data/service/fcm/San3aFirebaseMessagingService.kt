package com.paris_2.san3a.data.service.fcm

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.paris_2.san3a.R

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class San3aFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.data["title"] ?: "San3a"
        val body = remoteMessage.data["body"] ?: "New message received"

        if (!isAppInForeground()) {
            Log.d("NotificationTest", "App in background, receive notification")
            showNotification(title, body)
        } else {
            Log.d("NotificationTest", "App in foreground, ignoring notification")
        }
    }

    private fun isAppInForeground(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        val packageName = packageName
        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                appProcess.processName == packageName
            ) {
                return true
            }
        }
        return false
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "san3a_channel"
        val notificationId = System.currentTimeMillis().toInt()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "San3a Notifications"

            val attributes = android.media.AudioAttributes.Builder()
                .setUsage(android.media.AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                enableVibration(true)
                setSound(soundUri, attributes)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_logo)
            .setSound(soundUri)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}