package com.simple.weather.di.modules

import com.simple.weather.repository.SharedRepository
import com.simple.weather.repository.SharedRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesSharedRepository(sharedRepo: SharedRepositoryImpl): SharedRepository = sharedRepo
}