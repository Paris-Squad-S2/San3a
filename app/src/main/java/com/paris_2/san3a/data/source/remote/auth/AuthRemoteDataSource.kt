package com.paris_2.san3a.data.source.remote.auth

import com.paris_2.san3a.data.service.auth.WhatsAppMessage
import com.paris_2.san3a.data.source.remote.auth.dto.OtpDto

interface AuthRemoteDataSource {
    suspend fun sendMessage(body: WhatsAppMessage): OtpDto
}

