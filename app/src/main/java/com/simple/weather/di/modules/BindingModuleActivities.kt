package com.simple.weather.di.modules

import com.simple.weather.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModuleActivities {

    @ContributesAndroidInjector
    abstract fun contributeSTActivity(): MainActivity
}