package com.simple.weather.remote.model.response.nextDays

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Int?,

    @SerializedName("gust")
    val gust: Double?,

    @SerializedName("speed")
    val speed: Double?
)