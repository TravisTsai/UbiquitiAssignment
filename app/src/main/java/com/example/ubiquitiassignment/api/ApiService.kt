package com.example.ubiquitiassignment.api

import com.example.ubiquitiassignment.entity.AirPollutionDTO
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("v2/aqx_p_432")
    suspend fun getAirPollutionData(
        @Query("limit") limit: String = "1000",
        @Query("api_key") apiKey: String = "cebebe84-e17d-4022-a28f-81097fda5896",
        @Query("ort") ort: String = "ImportDate"
    ): AirPollutionDTO
}
