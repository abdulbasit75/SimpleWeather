package com.simple.weather.di.components

import com.simple.weather.SimpleWeather
import com.simple.weather.di.modules.*
import com.simple.weather.remote.network.NetworkService
import com.simple.weather.repository.SharedRepository
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidInjectionModule::class),
        (BindingModuleActivities::class),
        (BindingModuleFragments::class),
        (ViewModelModule::class),
        (RepositoryModule::class),
        (ContextModule::class),
        (NetworkModule::class),
    ]
)
interface ApplicationComponent : AndroidInjector<SimpleWeather> {
    val networkService: NetworkService

    // Add Repositories here:
    val sharedRepository: SharedRepository
}