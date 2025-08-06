package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.domain.entity.RequestService
import kotlinx.datetime.LocalDateTime

fun RequestServiceDto.toEntity() = RequestService(
    id = id,
    serviceType = serviceType,
    description = description,
    location = location,
    offers = offers,
    userId = userId,
    locationDetails = locationDetails,
    image = emptyList(),
    title = title,
    time = LocalDateTime.parse(time),
    state = state,
    selectedCraftsmanId = selectedCraftsmanId,
)

fun RequestService.toDto(imageUrls: List<String> = emptyList()) = RequestServiceDto(
    id = id,
    serviceType = serviceType,
    description = description,
    location = location,
    offers = offers,
    userId = userId,
    locationDetails = locationDetails,
    image = imageUrls,
    title = title,
    time = time.toString(),
    state = state,
    selectedCraftsmanId = selectedCraftsmanId,
)