package com.paris_2.san3a.data.utils

import com.paris_2.san3a.data.source.remote.messages.dto.MessageDto

fun Map<String, Any>.toMessageDto(): MessageDto? {
    return MessageDto.fromJson(this, this["id"]?.toString() ?: "")
}
