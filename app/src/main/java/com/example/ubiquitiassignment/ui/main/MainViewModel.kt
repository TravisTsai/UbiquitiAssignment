package com.example.ubiquitiassignment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ubiquitiassignment.entity.AirPollutionVO
import com.example.ubiquitiassignment.entity.Resource
import com.example.ubiquitiassignment.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel() {

    companion object {
        private const val THRESHOLD: Int = 10
    }

    private val _goodAirPollutions = MutableLiveData<Resource<List<AirPollutionVO>>>()
    val goodAirPollutions: LiveData<Resource<List<AirPollutionVO>>> = _goodAirPollutions

    private val _badAirPollutions = MutableLiveData<List<AirPollutionVO>>()
    val badAirPollutions: LiveData<List<AirPollutionVO>> = _badAirPollutions

    var airPollutionList: List<AirPollutionVO> = emptyList()
        private set

    fun fetchAirPollutions() {
        _goodAirPollutions.value = Resource.Loading()

        viewModelScope.launch {
            runCatching {
                airPollutionList = remoteRepository.getAirPollutionData()
                _goodAirPollutions.value =
                    Resource.Success(airPollutionList.filter { it.pm25.toInt() <= THRESHOLD })
                _badAirPollutions.value = airPollutionList.filter { it.pm25.toInt() > THRESHOLD }
            }.onFailure {
                Timber.e("getAirPollutions: Error=${it.message}")
                _goodAirPollutions.value = Resource.Error(message = it.message.orEmpty())
            }
        }
    }

}
