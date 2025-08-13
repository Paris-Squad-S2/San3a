package com.paris_2.san3a.data.service.location

import com.paris_2.san3a.data.source.remote.location.Country
import com.paris_2.san3a.data.source.remote.location.dto.CitiesDto
import com.paris_2.san3a.data.source.remote.location.dto.StatesDto
import com.paris_2.san3a.data.source.remote.location.request.CitiesRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LocationService {
    @POST("states")
    suspend fun getStates(@Body body: Country): StatesDto

    @POST("state/cities")
    suspend fun getCities(@Body body: CitiesRequest): CitiesDto

}