package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.user.dto.StatsDto
import com.paris_2.san3a.domain.entity.Stats

fun StatsDto.toEntity(): Stats {
    return Stats(
        userId = userId,
        jobsDone = jobsDone,
        earnings = earnings,
        rating = rating
    )
}