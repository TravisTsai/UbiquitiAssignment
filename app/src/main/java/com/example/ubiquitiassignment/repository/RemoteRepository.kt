package com.example.ubiquitiassignment.repository

import com.example.ubiquitiassignment.api.ApiService
import com.example.ubiquitiassignment.entity.AirPollutionVO
import com.example.ubiquitiassignment.entity.StatusEnum
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepository(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher) {

    @Inject
    constructor(apiService: ApiService) : this(apiService, Dispatchers.IO)

    suspend fun getAirPollutionData(): List<AirPollutionVO> = withContext(ioDispatcher) {
        apiService.getAirPollutionData().records?.map { recordsItem ->
            recordsItem?.let {
                AirPollutionVO(
                    siteId = if (it.siteId.isNullOrEmpty()) "-1" else it.siteId,
                    siteName = it.siteName ?: "",
                    pm25 = if (it.pm25.isNullOrEmpty()) "-1" else it.pm25,
                    county = it.county ?: "",
                    status = StatusEnum.safeValueOf(it.status)
                )
            } ?: run {
                AirPollutionVO(
                    siteId = "-1",
                    siteName = "",
                    pm25 = "-1",
                    county = "",
                    status = StatusEnum.UNKNOWN
                )
            }
        }?.distinct()?.filter { it.siteId != "-1" && it.pm25 != "-1" } ?: emptyList()
    }

}
