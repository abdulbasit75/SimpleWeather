package com.simple.weather.remote.model.response.nextDays

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double?
)