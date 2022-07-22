package com.simple.weather.repository

import com.simple.weather.common.base.baseModels.ResourceState
import com.simple.weather.common.base.baseModels.Status
import com.simple.weather.remote.model.response.nextDays.DaysForecast
import com.simple.weather.remote.model.response.nextHours.HoursForecast
import com.simple.weather.remote.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(private val mNetworkService: NetworkService) :
    SharedRepository {

    override fun getNextDaysWeather(timestampsCount: Int, latitude: String?, longitude: String?,
                                    unit: String): Flow<ResourceState<DaysForecast>> {
        return flow {
            try {
                val response =
                    mNetworkService.getNextDaysWeather(latitude, longitude, timestampsCount, unit)

                if (response.nextFiveDays == null)
                    response.nextFiveDays = mutableListOf()
                else
                    response.nextFiveDays!!.clear()

                for (index in 0 until 40 step 8) {
                    val day = response.list?.get(index)

                    if (day != null)
                        response.nextFiveDays!!.add(day)
                }

                emit(ResourceState(Status.SUCCESS, response, null))
            } catch (e: Exception) {
                emit(ResourceState(Status.ERROR, null, e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getNextHoursWeather(latitude: String?, longitude: String?,
                                     unit: String): Flow<ResourceState<HoursForecast>> {
        return flow {
            try {
                val response = mNetworkService.getNextHoursWeather(latitude, longitude, unit)
                emit(ResourceState(Status.SUCCESS, response, null))
            } catch (e: Exception) {
                emit(ResourceState(Status.ERROR, null, e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}


