package com.simple.weather.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Abdul Basit on 21-Jul-2022.
 */

@Parcelize
data class City(var name: String, var latitude: String, var longitude: String) : Parcelable