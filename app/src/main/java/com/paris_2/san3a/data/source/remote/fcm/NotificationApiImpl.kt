package com.paris_2.san3a.data.source.remote.fcm

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*

class NotificationApiImpl : NotificationApi {
    private val client = HttpClient(CIO)

    override suspend fun sendNotification(
        token: String,
        title: String,
        description: String
    ) {
        client.post(URL) {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "token" to token,
                    "title" to title,
                    "body" to description
                )
            )
        }
    }

    companion object {
        private const val URL = "https://push-notifications-backend.vercel.app/api/send"
    }
}
