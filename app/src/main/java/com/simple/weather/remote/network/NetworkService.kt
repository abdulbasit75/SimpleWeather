package com.simple.weather.remote.network

import com.simple.weather.remote.model.response.nextDays.DaysForecast
import com.simple.weather.remote.model.response.nextHours.HoursForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(EndPoints.NEXT_HOURS_FORECAST)
    suspend fun getNextHoursWeather(@Query("lat") latitude: String?,
                                    @Query("lon") longitude: String?,
                                    @Query("units") unit: String): HoursForecast

    @GET(EndPoints.NEXT_DAYS_FORECAST)
    suspend fun getNextDaysWeather(@Query("lat") latitude: String?,
                                   @Query("lon") longitude: String?,
                                   @Query("cnt") timestampsCount: Int,
                                   @Query("units") unit: String): DaysForecast
}