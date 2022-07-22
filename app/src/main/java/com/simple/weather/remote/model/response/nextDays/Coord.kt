package com.simple.weather.remote.model.response.nextDays

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val lat: Double?,

    @SerializedName("lon")
    val lon: Double?
)