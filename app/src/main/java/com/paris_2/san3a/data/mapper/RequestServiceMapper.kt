package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.domain.entity.RequestService

fun RequestServiceDto.toEntity() = RequestService(
    id = id,
    title = title,
    description = description,
    location = location,
    relatedJob = relatedJob,
    offers = offers,
    userId = userId,
    requestedCount = requestedCount

)

fun RequestService.toDto() = RequestServiceDto(
    id = id,
    title = title,
    description = description,
    location = location,
    relatedJob = relatedJob,
    offers = offers,
    userId = userId,
    requestedCount = requestedCount
)