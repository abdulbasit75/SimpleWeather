package com.simple.weather.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.simple.weather.remote.model.City
import com.simple.weather.ui.cityWeather.CityWeatherFragment
import com.simple.weather.utils.cities

/**
 * Created by Abdul Basit on 21-Jul-2022.
 */

class WeatherPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = cities.size

    override fun createFragment(position: Int): Fragment {
        val objCity = cities[position]
        return CityWeatherFragment.newInstance(objCity)
    }
}

