package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.data.utils.getCurrentDateTime
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import kotlinx.datetime.LocalDateTime

fun RequestServiceDto.toEntity() = RequestService(
    id = id,
    description = description,
    governorateId = governorateId,
    cityId = cityId,
    userId = userId,
    locationDetails = locationDetails,
    image = image,
    title = title,
    time = LocalDateTime.parse(time ?: getCurrentDateTime().toString()),
    serviceId = serviceId,
    selectedCraftsmanId = selectedCraftsmanId,
    requestStatus = RequestStatus.valueOf(requestStatus ?: RequestStatus.ONGOING.name)
)

fun RequestService.toDto(imageUrls: List<String> = emptyList()) = RequestServiceDto(
    id = id,
    description = description,
    governorateId = governorateId,
    cityId = cityId,
    userId = userId,
    locationDetails = locationDetails,
    image = imageUrls,
    title = title,
    time = time.toString(),
    serviceId = serviceId,
    selectedCraftsmanId = selectedCraftsmanId,
    requestStatus = requestStatus.name
)