package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.requestDetails.dto.OfferDto
import com.paris_2.san3a.data.utils.getCurrentDateTime
import com.paris_2.san3a.domain.entity.Offer
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

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
    preferredDate = runCatching { LocalDate.parse(preferredDate) }.getOrElse { getCurrentDateTime().date },
    preferredTime = runCatching { LocalTime.parse(preferredTime) }.getOrElse { getCurrentDateTime().time },
    createdAt = runCatching { LocalDateTime.parse(createdAt) }.getOrElse { getCurrentDateTime() },
    messageToCustomer = messageToCustomer,
    isAccepted = isAccepted
)