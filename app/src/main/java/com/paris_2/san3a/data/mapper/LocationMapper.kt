package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.location.dto.CitiesDto
import com.paris_2.san3a.data.source.remote.location.dto.StatesDto
import com.paris_2.san3a.domain.entity.Cities
import com.paris_2.san3a.domain.entity.States

fun StatesDto.toStates(): States {
    return States(
        names = this.data?.states?.mapNotNull { it?.name } ?: emptyList()
    )
}

fun CitiesDto.toCities(): Cities {
    return Cities(
        names = this.data ?: emptyList()
    )
}
