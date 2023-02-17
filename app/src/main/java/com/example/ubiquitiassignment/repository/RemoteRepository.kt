package com.example.ubiquitiassignment.repository

import com.example.ubiquitiassignment.api.ApiService
import com.example.ubiquitiassignment.entity.AirPollutionDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepository(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher) {

    @Inject
    constructor(apiService: ApiService) : this(apiService, Dispatchers.IO)

    suspend fun getAirPollution(): AirPollutionDTO = withContext(ioDispatcher) {
        apiService.getAirPollution()
    }

}