package com.simple.weather.remote.model.response.nextDays

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int?
)