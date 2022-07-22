package com.simple.weather.ui.cityWeather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.simple.weather.R
import com.simple.weather.databinding.NextHoursColumnBinding
import com.simple.weather.remote.model.response.nextHours.Hourly
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HoursAdapter(private val mContext: Context, private val mHoursList: ArrayList<Hourly?>)
    : RecyclerView.Adapter<HoursAdapter.HourHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
        val view = NextHoursColumnBinding.inflate(LayoutInflater.from(mContext),
            parent, false)
        return HourHolder(view)
    }

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        val objHour = mHoursList[position]
        val weatherIconURL = mContext.getString(R.string.icon_url, objHour?.weather?.first()?.icon)

        holder.viewBinding.weatherIconIv.load(weatherIconURL)
        holder.viewBinding.timeTv.text = holder.getTime(objHour?.dt)
        holder.viewBinding.humidityTv.text = "${objHour?.humidity} %"
        holder.viewBinding.tempTv.text = "${objHour?.temp?.toInt()} \u2103"
    }

    override fun getItemCount(): Int = mHoursList.size

    fun updateList(daysList: List<Hourly?>) {
        mHoursList.clear()
        mHoursList.addAll(daysList)
        notifyDataSetChanged()
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    inner class HourHolder(var viewBinding: NextHoursColumnBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun getTime(dt: Long?): String {
            val df = SimpleDateFormat(mContext.getString(R.string.hour_format), Locale.US)
            return df.format(Date(dt!! * 1000))
        }
    }
}