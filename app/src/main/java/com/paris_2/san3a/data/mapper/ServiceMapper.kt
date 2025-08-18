package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.requestDetails.dto.OfferDto
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.data.utils.getCurrentDateTime
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Service
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

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

fun RequestServiceDto.toEntity() = RequestService(
    id = id,
    description = description,
    governorateId = governorateId,
    cityId = cityId,
    userId = userId,
    locationDetails = locationDetails,
    image = image,
    title = title,
    time = LocalDateTime.Companion.parse(time ?: getCurrentDateTime().toString()),
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

fun Offer.toDto()  = OfferDto(
    id = id,
    requestId = requestId,
    craftsmanId = craftsmanId,
    price = price,
    preferredDate = preferredDate.toString(),
    preferredTime = preferredTime.toString(),
    createdAt = createdAt.toString(),
    messageToCustomer = messageToCustomer,
    isAccepted = isAccepted
)

fun OfferDto.toEntity() = Offer(
    id = id,
    requestId = requestId,
    craftsmanId = craftsmanId,
    price = price,
    preferredDate = runCatching { LocalDate.Companion.parse(preferredDate) }.getOrElse { getCurrentDateTime().date },
    preferredTime = runCatching { LocalTime.Companion.parse(preferredTime) }.getOrElse { getCurrentDateTime().time },
    createdAt = runCatching { LocalDateTime.parse(createdAt) }.getOrElse { getCurrentDateTime() },
    messageToCustomer = messageToCustomer,
    isAccepted = isAccepted
)