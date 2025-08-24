package com.paris_2.san3a.domain.usecase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.tasks.await

class SendNotificationUseCase {

    suspend operator fun invoke(title: String, token: String, description: String): Boolean {
        return try {
            val message = RemoteMessage.Builder(token)
                .setMessageId(System.currentTimeMillis().toString())
                .addData("title", title)
                .addData("body", description)
                .build()

            FirebaseMessaging.getInstance().send(message)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
