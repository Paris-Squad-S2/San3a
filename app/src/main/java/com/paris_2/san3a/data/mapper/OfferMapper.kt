package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.requestDetails.dto.OfferDto
import com.paris_2.san3a.domain.entity.Offer

fun Offer.toDto()  = OfferDto(
    id = id,
    requestId = requestId,
    craftsmanId = craftsmanId,
    price = price.coerceIn(MinPrice,MaxPrice),
    preferredDate = preferredDate.toString(),
    preferredTime = preferredTime.toString(),
    messageToCustomer = messageToCustomer,
    isAccepted = isAccepted
)

fun OfferDto.toEntity() = Offer(
    id = id,
    requestId = requestId,
    craftsmanId = craftsmanId,
    price = price.coerceIn(MinPrice,MaxPrice),
    preferredDate = kotlinx.datetime.LocalDate.parse(preferredDate),
    preferredTime = kotlinx.datetime.LocalTime.parse(preferredTime),
    messageToCustomer = messageToCustomer,
    isAccepted = isAccepted
)
private const val MinPrice = 0.0
private const val MaxPrice  = 900000.0