package com.paris_2.san3a.data.source.local.location

import android.content.Context
import com.paris_2.san3a.data.source.local.location.dto.CityDto
import com.paris_2.san3a.data.source.local.location.dto.GovernorateDto
import kotlinx.serialization.json.Json

class LocationLocalDataSourceImpl(private val context: Context) : LocationLocalDataSource {

    private val jsonObject = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    override fun getGovernorates(): List<GovernorateDto> = readJsonList(GOVERNORATES_FILE)

    fun getAllCities(): List<CityDto> = readJsonList(CITIES_FILE)

    override fun getCities(governorateId: Int): List<CityDto> = getAllCities()
        .filter { city -> city.governorateId == governorateId }

    override fun getGovernorateById(governorateId: Int): GovernorateDto? {
        return getGovernorates().let { governorateDtos ->
            governorateDtos.find { it.id == governorateId }
        }
    }

    override fun getCityById(cityId: Int): CityDto? {
        return getAllCities().let { cityDtos ->
            cityDtos.find { it.id == cityId }
        }
    }

    private inline fun <reified T> readJsonList(fileName: String): List<T> {
        val jsonString = getJsonString(fileName) ?: return emptyList()
        return try {
            jsonObject.decodeFromString<List<T>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun getJsonString(fileName: String): String? = try {
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    companion object {
        private const val GOVERNORATES_FILE = "governorates.json"
        private const val CITIES_FILE = "cities.json"
    }
}