package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.domain.entity.Service

fun ServiceDto.toEntity(isDarkTheme: Boolean): Service {
    return Service(
        id = id,
        title = title,
        description = description,
        imageUrl = if (isDarkTheme) darkImageUrl else imageUrl
    )
}

fun List<ServiceDto>.toEntity(isDarkTheme: Boolean): List<Service> {
    return map { it.toEntity(isDarkTheme) }
}
