package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.domain.entity.MostRequestedServices

fun RequestServiceDto.toMostRequestedServices(): MostRequestedServices {
    return MostRequestedServices(
        id = id,
        serviceType = serviceType,
        description = description,
    )
}