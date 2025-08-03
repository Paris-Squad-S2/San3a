package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.domain.entity.RequestService

fun RequestServiceDto.toEntity() = RequestService(
    id = id,
    serviceType = serviceType,
    customerComplain = "",
    description = description,
    location = location,
    relatedJob = relatedJob,
    offers = offers,
    userId = userId,
    requestedCount = requestedCount,
    locationDetails = "",
    image = emptyList()
)

fun RequestService.toDto() = RequestServiceDto(
    id = id,
    serviceType = serviceType,
    customerComplain = customerComplain,
    description = description,
    location = location,
    relatedJob = relatedJob,
    offers = offers,
    userId = userId,
    requestedCount = requestedCount,
    locationDetails = locationDetails,
    image = image
)