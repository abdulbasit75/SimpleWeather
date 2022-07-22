package com.simple.weather.remote.model.response.nextDays

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String?
)