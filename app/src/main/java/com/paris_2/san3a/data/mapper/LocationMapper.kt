package com.paris_2.san3a.data.mapper


import com.paris_2.san3a.data.source.local.location.dto.CityDto
import com.paris_2.san3a.data.source.local.location.dto.GovernorateDto
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate

fun List<GovernorateDto>.toStates(language: String): List<Governorate> {
    return this.map { it.toGovernorate(language) }
}

fun GovernorateDto.toGovernorate(
    language: String
): Governorate {
    return Governorate(
        id = this.id,
        name = if (language == "ar") this.arabicName else this.englishName
    )
}

fun List<CityDto>.toCities(language: String): List<City> {
    return this.map { it.toCity(language) }
}

fun CityDto.toCity(language: String): City {
    return City(
        id = this.id,
        name = if (language == "ar") this.arabicName else this.englishName,
        governorateId = this.governorateId
    )
}