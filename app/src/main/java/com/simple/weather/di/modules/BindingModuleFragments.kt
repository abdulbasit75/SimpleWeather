package com.simple.weather.di.modules

import com.simple.weather.common.base.BaseFragment
import com.simple.weather.ui.cityWeather.CityWeatherFragment
import com.simple.weather.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModuleFragments {

    // Bind every new fragment here, following the BaseFragment example:
    @ContributesAndroidInjector
    abstract fun contributeBaseFragment(): BaseFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeCityWeatherFragment(): CityWeatherFragment
}