package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.requestDetails.dto.OfferDto
import com.paris_2.san3a.domain.entity.Offer

fun Offer.toDto()  = OfferDto(
    id = id,
    requestId = requestId,
    craftsmanId = craftsmanId,
    price = price,
    preferredDate = preferredDate,
    preferredTime = preferredTime,
    messageToCustomer = messageToCustomer,
    isAccepted = isAccepted
)

fun OfferDto.toEntity() = Offer(
    id = id,
    requestId = requestId,
    craftsmanId = craftsmanId,
    price = price,
    preferredDate = preferredDate,
    preferredTime = preferredTime,
    messageToCustomer = messageToCustomer,
    isAccepted = isAccepted
)