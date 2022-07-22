package com.simple.weather.remote.model.response.nextHours

import com.google.gson.annotations.SerializedName

data class HoursForecast(
    @SerializedName("hourly")
    val hourlyList: List<Hourly?>?,

    @SerializedName("lat")
    val lat: Double?,

    @SerializedName("lon")
    val lon: Double?,

    @SerializedName("timezone")
    val timezone: String?,

    @SerializedName("timezone_offset")
    val timezoneOffset: Int?
)