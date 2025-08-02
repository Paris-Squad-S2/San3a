package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.location.dto.StatesDto
import com.paris_2.san3a.domain.entity.States

fun StatesDto.toStates(): States {
    return States(
        names = this.states?.states?.mapNotNull { it?.name } ?: emptyList()
    )
}