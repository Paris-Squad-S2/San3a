package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.domain.entity.Service

fun ServiceDto.toEntity(isDarkTheme: Boolean, language: String): Service {
    return Service(
        id = id,
        title = title[language] ?: title["en"] ?: "",
        description = description[language] ?: description["en"] ?: "",
        imageUrl = if (isDarkTheme) darkImageUrl else imageUrl,
        suggestions = suggestions[language] ?: suggestions["en"] ?: emptyList(),
    )
}

fun List<ServiceDto>.toEntity(isDarkTheme: Boolean, language: String): List<Service> {
    return map { it.toEntity(isDarkTheme = isDarkTheme, language = language) }
}
