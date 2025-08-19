package com.paris_2.san3a.data.service.auth

data class WhatsAppMessage (
    val phoneNumber: String,
    val message: String,
    val sessionId: String =  "662d3967-d32e-4c95-9e60-6ff8e037cc2b"
)