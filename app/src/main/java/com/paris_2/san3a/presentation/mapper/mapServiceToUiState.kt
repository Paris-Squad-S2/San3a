package com.paris_2.san3a.presentation.mapper

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.presentation.screen.account.ServiceUiState
import kotlin.collections.map

fun mapServiceToUiState(services: List<Service>): List<ServiceUiState> {
        val currentLocale = "englishName"
        return services.map {
            ServiceUiState(
                id = it.id,
                serviceTitle = it.title[currentLocale] ?: it.title.values.firstOrNull() ?: "",
                serviceDescription = it.description[currentLocale]
                    ?: it.description.values.firstOrNull() ?: "",
                isSelected = false,
                serviceImage = it.imageUrl
            )
        }
    }