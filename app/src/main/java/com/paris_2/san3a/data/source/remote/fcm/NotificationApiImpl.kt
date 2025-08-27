package com.paris_2.san3a.data.source.remote.fcm

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient

class NotificationApiImpl: NotificationApi {
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
        private const val URL = "http://10.0.2.2:3000/send"
    }
}