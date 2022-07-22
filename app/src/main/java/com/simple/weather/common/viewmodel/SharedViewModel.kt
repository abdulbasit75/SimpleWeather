package com.simple.weather.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.weather.common.base.baseModels.ResourceState
import com.simple.weather.remote.model.response.nextDays.DaysForecast
import com.simple.weather.remote.model.response.nextHours.HoursForecast
import com.simple.weather.repository.SharedRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Abdul Basit on 21-Jul-2022.
 */

class SharedViewModel @Inject constructor(private val mSharedRepository: SharedRepository) :
    ViewModel() {

    private val _nextDaysForecast = MutableLiveData<ResourceState<DaysForecast>>()
    val nextDaysForecast: LiveData<ResourceState<DaysForecast>> = _nextDaysForecast

    private val _nextHoursForecast = MutableLiveData<ResourceState<HoursForecast>>()
    val nextHoursForecast: LiveData<ResourceState<HoursForecast>> = _nextHoursForecast

    fun getNextDaysWeather(timestampsCount: Int, latitude: String?, longitude: String?,
                           unit: String) {
        viewModelScope.launch {
            mSharedRepository.getNextDaysWeather(timestampsCount, latitude, longitude, unit)
                .collect {
                    _nextDaysForecast.postValue(it)
                }
        }
    }

    fun getNextHoursWeather(latitude: String?, longitude: String?, unit: String) {
        viewModelScope.launch {
            mSharedRepository.getNextHoursWeather(latitude, longitude, unit).collect {
                _nextHoursForecast.postValue(it)
            }
        }
    }
}
