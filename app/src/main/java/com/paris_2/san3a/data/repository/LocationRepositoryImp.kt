package com.paris_2.san3a.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.paris_2.san3a.data.mapper.toLocation
import com.paris_2.san3a.data.repository.service.LocationService
import com.paris_2.san3a.domain.LocationErrorException
import com.paris_2.san3a.domain.UnKnownCityException
import com.paris_2.san3a.domain.UnKnownCountryException
import com.paris_2.san3a.domain.entity.AppLocation
import com.paris_2.san3a.domain.repository.LocationRepository
import java.util.Locale

@SuppressLint("MissingPermission")
class LocationRepositoryImp(
    private val context: Context,
    private val locationService: LocationService,
) : LocationRepository {

    override suspend fun getCurrentLocation(): AppLocation {
        val locationDto = locationService.getCurrentLocation() ?: throw LocationErrorException()

        val cityName = getCityName(locationDto.latitude, locationDto.longitude)
            ?: throw UnKnownCityException()
        val countryName = getCountryName(locationDto.latitude, locationDto.longitude)
            ?: throw UnKnownCountryException()

        return locationDto.toLocation().copy(cityName = cityName, countryName = countryName)
    }

    private fun getCityName(latitude: Double, longitude: Double): String? {
        val cityName: String?
        val geoCoder = Geocoder(context, Locale.getDefault())
        val address = geoCoder.getFromLocation(latitude, longitude, 3)?.toList()

        if (address.isNullOrEmpty()) return null

        cityName = address[0].adminArea
        return cityName
    }

    private fun getCountryName(latitude: Double, longitude: Double): String? {
        val cityName: String?
        val geoCoder = Geocoder(context, Locale.getDefault())
        val address = geoCoder.getFromLocation(latitude, longitude, 3)?.toList()

        if (address.isNullOrEmpty()) return null

        cityName = address[0].countryName
        return cityName
    }

}