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
    requestedCount = requestedCount,
    locationDetails = "" ,
    image = emptyList()
)

fun RequestService.toDto(imageUrls: List<String>) = RequestServiceDto(
    id = id,
    title = title,
    description = description,
    location = location,
    relatedJob = relatedJob,
    offers = offers,
    userId = userId,
    requestedCount = requestedCount,
    images = imageUrls
)