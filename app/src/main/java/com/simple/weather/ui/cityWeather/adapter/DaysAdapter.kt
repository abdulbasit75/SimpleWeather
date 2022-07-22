package com.simple.weather.ui.cityWeather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.simple.weather.R
import com.simple.weather.databinding.NextDaysItemRowBinding
import com.simple.weather.remote.model.response.nextDays.Day
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DaysAdapter(private val mContext: Context, private val mDaysList: ArrayList<Day>)
    : RecyclerView.Adapter<DaysAdapter.DayHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = NextDaysItemRowBinding.inflate(LayoutInflater.from(mContext),
            parent, false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val objDay = mDaysList[position]
        val weatherIconURL = objDay.weather?.first()?.icon

        holder.viewBinding.dayDetail.text = holder.getDayDate(objDay.dt)
        holder.viewBinding.minTemp.text = "${objDay.main?.tempMin?.toInt()} \u2103"
        holder.viewBinding.maxTemp.text = "${objDay.main?.tempMax?.toInt()} \u2103"
        holder.viewBinding.dayDescription.text = "${objDay.weather?.first()?.description}"
        holder.viewBinding.weatherIconIv.load(mContext.getString(R.string.icon_url, weatherIconURL))
    }

    override fun getItemCount(): Int = mDaysList.size

    fun updateList(daysList: List<Day>) {
        mDaysList.clear()
        mDaysList.addAll(daysList)
        notifyDataSetChanged()
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    inner class DayHolder(var viewBinding: NextDaysItemRowBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun getDayDate(dt: Long?): String {
            val df = SimpleDateFormat(mContext.getString(R.string.day_date_format), Locale.US)
            return df.format(Date(dt!! * 1000))
        }
    }
}