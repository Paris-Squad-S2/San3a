package com.paris_2.san3a.data.source.remote.fcm

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable

class NotificationApiImpl : NotificationApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun sendNotification(
        token: String,
        title: String,
        description: String
    ) {
        try {
            Log.d("NotificationTest", ">>> Sending notification request")
            val response: HttpResponse = client.post(URL) {
                contentType(ContentType.Application.Json)
                setBody(
                    NotificationRequest(
                        token = token,
                        title = title,
                        body = description
                    )
                )
            }
            Log.d("NotificationTest", ">>> Response status: ${response.status.value}")
            Log.d("NotificationTest", ">>> Response body: ${response.bodyAsText()}")
        } catch (e: Exception) {
            Log.e("NotificationTest", ">>> Error sending notification", e)
        }
    }

    companion object {
        private const val URL = "https://push-notifications-backend.vercel.app/api/send"
    }
}

@Serializable
data class NotificationRequest(
    val token: String,
    val title: String,
    val body: String
)
