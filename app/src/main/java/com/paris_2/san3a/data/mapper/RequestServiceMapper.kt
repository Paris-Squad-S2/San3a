package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.data.utils.getCurrentDate
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import kotlinx.datetime.LocalDateTime

fun RequestServiceDto.toEntity() = RequestService(
    id = id,
    serviceType = serviceType,
    description = description,
    location = location,
    offers = offers,
    userId = userId,
    locationDetails = locationDetails,
    image = image,
    title = title,
    time = LocalDateTime.parse(time ?: getCurrentDate().toString()),
    state = state,
    selectedCraftsmanId = selectedCraftsmanId,
    requestStatus = RequestStatus.valueOf(requestStatus ?: RequestStatus.ONGOING.name)
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
    requestStatus = requestStatus.name
)