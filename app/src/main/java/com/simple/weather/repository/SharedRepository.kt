package com.simple.weather.repository

import com.simple.weather.common.base.baseModels.ResourceState
import com.simple.weather.remote.model.response.nextDays.DaysForecast
import com.simple.weather.remote.model.response.nextHours.HoursForecast
import kotlinx.coroutines.flow.Flow

interface SharedRepository {

    fun getNextDaysWeather(timestampsCount: Int, latitude: String?, longitude: String?,
                           unit: String): Flow<ResourceState<DaysForecast>>

    fun getNextHoursWeather(latitude: String?, longitude: String?, unit: String)
            : Flow<ResourceState<HoursForecast>>
}