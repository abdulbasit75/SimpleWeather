package com.simple.weather.remote.model.response.nextDays

import com.google.gson.annotations.SerializedName

data class DaysForecast(
    @SerializedName("city")
    val city: City?,

    @SerializedName("cnt")
    val cnt: Int?,

    @SerializedName("cod")
    val cod: String?,

    @SerializedName("list")
    val list: List<Day>?,

    @SerializedName("message")
    val message: Int?,

    var nextFiveDays: MutableList<Day>?
)