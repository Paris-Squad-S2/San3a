package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.domain.entity.Service

fun ServiceDto.toEntity(): Service {
    return Service(
        id = id,
        title = title,
        description = description
    )
}

fun Service.toDto(): ServiceDto {
    return ServiceDto(
        id = id,
        title = title,
        description = description
    )
}
